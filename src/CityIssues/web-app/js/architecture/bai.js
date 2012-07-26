var bai = {};
bai.module = {};

if(!console){
    var console = {
        log:function(){},
        error:function(){},
        info:function(){},
        debug:function(){}
    }
}

bai._signInDialog = null;
bai._signUpDialog = null;
bai._feedbackDialog = null;

bai.Sandbox = function(name, extendObj){
    var that = {};
    if(extendObj){
        that = new extendObj();
    }
    var baseUrl = "";
    var dialogs = {};
    
    var requests = {};

    // configure form validator
    $.validator.messages.required = "Campo obrigatório";
    $.validator.messages.equalTo = "Deve ser igual à senha digitada";
    $.validator.messages.email = "Endereço de Email inválido";
    $.validator.messages.minlength = "Deve ter no mínimo {0} caracteres";
    
    that.readCookie = function(name){
        return $.cookie(name);
    }
    
    that.writeCookie = function(name, value){
        $.cookie(name, value);
    }
    
    that.deleteCookie = function(name){
        $.cookie(name, null);
    }
    
    that.getDialogs = function(){
        return dialogs;
    }

    that.addDialog = function(name, dialog){
        dialogs[name] = dialog;
    }

    that.showDialog = function(name, params){
        if(dialogs[name]){
            dialogs[name].show.apply(that, params);
        }
    }

    that.hideDialog = function(name){
        if(dialogs[name]){
            dialogs[name].hide();
        }
    }

    that.preloadImages = function(listOrImages, base){
        if(!$.isArray(listOrImages))
            return;
        if(typeof base != "string" ) base = "";
        for(var i=0; i<listOrImages.length; i++){
            (new Image()).src = base + listOrImages[i];
        }
    }

    /**
     * i18n messages from javascript
     */
    that.msg = function(code){
        if(window._message){
            return _message[code];
        } else {
            try{
                var list = code.split(".");
                var o;
                for(var i=0; i<list.length; i++){
                    if(i == 0){
                        o = messages[list[i]];
                    } else {
                        o = o[list[i]];
                    }

                    if(!o){
                        break;
                    } else {
                        if($.isFunction(o)){
                            return o.call();
                        }
                    }
                }
            }catch(e){}
        }
        try{
            console.log("Message code not found: " + code)
        } catch(e) {};
        return code;
    }

    

    that.showSignUp = function(requestArguments){
        that.getDialog("signUp").show();
    };

    that.showFeedback = function(requestArguments){
        that.getDialog("feedback").show();
    };

    that.showSignIn = function(requestArguments){
        that.getDialog("signIn").show(function(resultJson){
            // update user info
            if(window._user){
                _user.email = resultJson.username;
            }

            // update page elements after the login
            if(resultJson.updateElements){
                for( var id in resultJson.updateElements ){
                    $("#" +id).html(resultJson.updateElements[id]);
                }
            }

            // call the saved request if exists
            if(requestArguments && requestArguments.length > 0){
                that.requestJson.apply(this, requestArguments);
            }
        }, that, requestArguments);
    }
    
    that.getRequestName = function(url){
        var split = url.split("/");
        var name = false;
        if(split.length >= 3){
            name = split[0] + split[1] + split[2];
        }
        return name;
    }

    that.abortCurrentRequest = function(url){        
        var requestName = that.getRequestName(url);
        if(requestName && requests[requestName]){
            try{
                requests[requestName].abort();
                
            }catch(e){}
            requests[requestName] = null;
        }
    }
    
    /**
     * Ajax request for json response
     * @param method <String> 'POST' or 'GET'
     * @param url <String>
     * @param callback <Function> The callback function
     * @param scope <Object> The object to be used as scope of the callback function
     * @param data <String|Object> The POST data
     */
    that.requestJson = function(method, url, callback, scope, data){
        that.abortCurrentRequest(url);
        
        var requestArguments = arguments;
        var requestObj = $.ajax({
            type: method,
            url: baseUrl + url,
            success: function(json){
                callback.call(scope, json);
            },
            error: function(jqXHR, textStatus, errorThrown){
                if(jqXHR.status == 401){
                    // Show the sign in dialog when we have an authentication error
                    that.showSignIn(requestArguments);
                }
            },
            dataType: "json",
            data:data
        });
        requests[that.getRequestName(url)] = requestObj;
        
        return requestObj;
    };
    
    that.requestHtml = function(method, url, callback, scope, data){
        that.abortCurrentRequest(url);
        
        var requestObj = $.ajax({
            type: method,
            url: baseUrl + url,
            success: function(json){
                callback.call(scope, json);
            },
            dataType: "html",
            data:data
        });
        
        requests[that.getRequestName(url)] = requestObj;
        
        return requestObj;
    }
        
    that.log = function(obj){
        try{console.log(obj);}catch(e){}
    }

    that.getDialog = function(name){
        return dialogs[name];
    }

    if(!bai._signInDialog)
        bai._signInDialog = new bai.SignInDialog(that);
    if(!bai._signUpDialog)
        bai._signUpDialog = new bai.SignUpDialog(that);
    if(!bai._feedbackDialog)
        bai._feedbackDialog = new bai.FeedbackDialog(that);
    
    that.addDialog("signIn", bai._signInDialog);
    that.addDialog("signUp", bai._signUpDialog);
    that.addDialog("feedback", bai._feedbackDialog);
    
    return that;
}

bai.Module = function( id ){
    var el = $("#"+id);
    return {
        el: el,
        fireEvent: function(name, obj){
            el.trigger(name, obj)
        },
        on: function(eventName, fn, scope, obj){
            el.bind(eventName, function(e, evData, value){
                fn.call(scope, evData);
            });
        }
    };
}




