/*
 * Should be used with the login/signInDialog template.
 * The id is "signInDialog".
 *
 * dependencies: bai.js, dialog.js
 */
bai.SignInDialog = function(sandbox){
    var id = "signInDialog";
    var that = {};
    
    var GOOGLE_OPENID = "https://www.google.com/accounts/o8/id?openid.ax.required=email,firstname";
    
    var dialog = new bai.Dialog(sandbox, {
        id:id,
        width:"auto",
        create: function(event, ui) {
            el.dialog("widget").addClass("signInDialog");
            el.find("div.footer").appendTo(el.dialog("widget"));
            $("#loginWithGoogleButton").button({icons: {primary:'ui-icon-google'}});
        },
        buttons:{
            cancel: {
                text:sandbox.msg("js.button.cancel"),
                click: function(ev){that.cancelButtonListener.call(that, ev);},
                icon: 'ui-icon-alert'
            },
            send:{
                text:sandbox.msg("js.button.signIn"),
                click: function(ev){that.submitButtonListener.call(that, ev);}
            }
        }
    });
    var el = dialog.el;

    // Show the signUp dialog
    $("#signInSignUpLink").click(function(){
        that.hide();
        setTimeout(function(){
            sandbox.showSignUp();
        },240);
    });
    
    
    var init = false;
    
    $.extend(that, dialog, {
        show: function(callbackFn, callbackScope, requestArguments){
            this.callbackFn = callbackFn;
            this.callbackScope = callbackScope;
            this.requestArguments = requestArguments;
            
            dialog.show();
            if(!init){
                init=true;
                this.initEvents();
            }
        },

        initEvents: function(){
            // Submit on enter
            el.keydown(function(ev){
                if (ev.keyCode == '13') {
                    ev.preventDefault();
                    ev.stopPropagation();
                    that.submitButtonListener.call(that, ev);
                }
            });
            
            
            $("#loginWithGoogleButton").live("click", function(ev){
                that.loginWithGoogleButtonListener.call(that, ev);
            });
            
            el.find("form").change(function(){
                var passField = el.find("#signInPasswordField");
                if(that.getValue("haspass") == "false"){
                    passField.attr("disabled", "true");
                    passField.addClass("ui-state-disabled");
                } else {
                    passField.removeAttr("disabled");
                    passField.removeClass("ui-state-disabled");
                }
            });
        },
        
        loginWithGoogleButtonListener: function(ev){
            this.setValue("openid", GOOGLE_OPENID);
            // request openid auth
            window.handleOpenIDResponse = function handleOpenIDResponse(jsonResult) {
                that.handleResult(jsonResult);
            }
            window.open('/openId/popup?', 'openid_popup', 'width=790,height=580');
            el.find("form")[0].submit();
        }, 

        submitButtonListener: function(ev){
            if(this.getValue("haspass") == "false"){
                // get email value and set 
                var email = this.getValue("j_username"),
                    openid = false;
                
                // check email address and set the openid provider
                if(email.indexOf("@gmail.com") >=0){
                    openid = "https://www.google.com/accounts/o8/id?openid.ax.required=email,firstname";
                } else if(email.indexOf("@yahoo.com") >=0){
                    openid = "https://www.yahoo.com?openid.ax.required=email,firstname";
                }
                
                if(openid){
                    this.setValue("openid", openid);
                    // request openid auth
                    window.handleOpenIDResponse = function handleOpenIDResponse(jsonResult) {
                        that.handleResult(jsonResult);
                    }
                    window.open('/openId/popup?', 'openid_popup', 'width=790,height=580');
                    el.find("form")[0].submit();
                } else {
                    // show sign up dialog
                    that.hide();
                    setTimeout(function(){
                        sandbox.showSignUp();
                        sandbox.getDialog("signUp").setValue("email", email);
                    },240);
                }
            } else {
                ev.preventDefault();
                this.resetForm();
                if(!that.validateForm())
                    return;
            
                var formData = this.getFormData();            
                this.wait(true);
                sandbox.requestJson("POST", "/j_spring_security_check", function(json){
                    that.handleResult(json);
                }, this, formData );
            }
        },
        
        handleResult: function(json){
            if(json.success){
                that.hide();
                if(that.callbackFn && that.callbackScope){
                    that.callbackFn.call(that.callbackScope, json);
                }
                that.fireEvent("signIn", {result:json});
            } else {
                that.wait(false);
                that.showErrors({_spring_security_remember_me: json.errorMsg});
            }
        },

        onClose: function(){
            that.fireEvent("beforeHide");
            that.resetForm();
            that.clear();
            
            // Cancel the original request if exists
            if( that.requestArguments && that.requestArguments.length >=4 ){
                var callback = that.requestArguments[2];
                var scope = that.requestArguments[3];
                callback.call(scope, {success:false, status:"authError"});
                that.fireEvent("hide");
            } else {
                that.fireEvent("hide");
            }
        },

        cancelButtonListener: function(ev){
            that.resetForm();
            this.hide();
        }
    });
    
    dialog.onClose = that.onClose;
    
    return that;
}