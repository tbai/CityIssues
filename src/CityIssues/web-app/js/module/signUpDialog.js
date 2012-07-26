/*
 * Should be used with the login/signUpDialog template.
 * The id is "signUpDialog".
 *
 * dependencies: bai.js, dialog.js
 */
bai.SignUpDialog = function(sandbox){
    var id = "signUpDialog";
    var that = {};

    var buttonsConfig = {
        normal:{
            cancel: {
                text:sandbox.msg("js.button.cancel"),
                click: function(ev){that.cancelButtonListener.call(that, ev);}
            },
            submit:{
                text:sandbox.msg("js.button.submitSignUp"),
                click: function(ev){that.saveButtonListener.call(that, ev);}
            }
        },
        result: {
            close: {
                text:sandbox.msg("js.button.close"),
                click: function(ev){that.closeButtonListener.call(that, ev);}
            }
        }
    }

    var dialog = new bai.Dialog(sandbox, {
        id:id,
        width:"auto",
        rules:{
            name: {required: true, minlength: 2},
            email: {required: true},
            terms: {required: true},
            password: {required: true, minlength: 6},
            confirmPassword: {required: true, equalTo: "#signUpPasswordField"}
        },
        buttons:buttonsConfig.normal
    });
    var el = dialog.el;

    el.keydown(function(ev){
        if (ev.keyCode == '13') {
            ev.preventDefault();
            that.saveButtonListener.call(that, ev);
        }
    });
    
    $.extend(that, dialog, {
        show: function(){
            // remove the result message and show the default form
            this.showResultMessage();
            dialog.show();
        },

        saveButtonListener: function(ev){
            ev.preventDefault();
            this.resetForm();
            if(!this.validateForm())
                return;
            
            that.postRequest();
        },

        closeButtonListener: function(ev){
            this.hide();
        },

        showResultMessage: function(html){
            var resultEl = el.find(".resultMessage");

            if(html){
                var visibleEl = el.find("form");
                resultEl.width(visibleEl.width());
                resultEl.height(visibleEl.height());
                resultEl.html(html);
                el.dialog("option", "buttons", buttonsConfig.result);
                el.addClass("success");
            }else{
                el.removeClass("success");
                el.dialog("option", "buttons", buttonsConfig.normal);
                resultEl.html("");
            }
            
        },

        postRequest: function(){
            var formData = this.getFormData();
            this.wait(true);
            sandbox.log("formData=" + formData);
                
            sandbox.requestJson("POST", "user/save", function(json){
                if(json.success){
                    this.showResultMessage(json.html);
                    this.fireEvent("createUser", {result:json});
                } else {
                    this.showErrors(json.errors);
                }
                this.wait(false);
            }, this, formData );
        },

        cancelButtonListener: function(ev){
            this.hide();
        }
    });

    return that;
}
