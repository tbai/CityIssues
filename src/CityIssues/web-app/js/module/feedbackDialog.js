/*
 * Should be used with the login/signInDialog template.
 * The id is "signInDialog".
 *
 * dependencies: mapUtils.js, bai.js
 */
bai.FeedbackDialog = function(sandbox){
    var id = "feedbackDialog";
    var that = {};
    var buttonsConfig = {
        normal:{
            cancel: {
                text:sandbox.msg("js.button.cancel"),
                click: function(ev){that.cancelButtonListener.call(that, ev);},
                icon: 'ui-icon-alert'
            },
            send:{
                text:sandbox.msg("js.button.send"),
                click: function(ev){that.submitButtonListener.call(that, ev);}
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
        buttons:{
            cancel: {
                text:sandbox.msg("js.button.cancel"),
                click: function(ev){that.cancelButtonListener.call(that, ev);},
                icon: 'ui-icon-alert'
            },
            send:{
                text:sandbox.msg("js.button.send"),
                click: function(ev){that.submitButtonListener.call(that, ev);}
            }
        }
    });
    var el = dialog.el;

    el.keydown(function(ev){
        if (ev.keyCode == '13') {
            ev.preventDefault();
            that.submitButtonListener.call(that, ev);
        }
    });
    
    $.extend(that, dialog, {
        show: function(){
            this.showResultMessage();
            var emailField = el.find('input[name="email"]');
            dialog.show();

            if(window._user && _user.email){
                emailField.val(_user.email);
                //emailField.attr("disabled", "true");
            } else  {
                emailField.val("");
                //emailField.attr("disabled", null);
            }
        },

        submitButtonListener: function(ev){
            ev.preventDefault();
            this.resetForm();
            if(!this.validateForm())
                return;

            var formData = this.getFormData();
            this.wait(true);
            
            sandbox.requestJson("POST", el.find("form").attr("action"), function(json){
                if(json.success){
                    this.wait(false);
                    this.showResultMessage(json.html);
                    this.fireEvent("feedbackSent", {result:json});
                } else {
                    this.wait(false);
                    this.showErrors(json.errors);
                }
            }, this, formData );
        },

        cancelButtonListener: function(ev){
            that.resetForm();
            this.hide();
        },
        
        closeButtonListener: function(ev){
            this.hide();
            this.showResultMessage();
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

        }
    });

    return that;
}