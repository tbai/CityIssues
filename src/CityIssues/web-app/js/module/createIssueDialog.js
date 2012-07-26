/*
 * Should be used with the issue/createDialog template.
 * The id is "createIssueDialog".
 *
 * dependencies: mapUtils.js, bai.js
 */
bai.CreateIssueDialog = function(sandbox){
    var id = "createIssueDialog";
    var that = {};
    var dialog = new bai.Dialog(sandbox, {
        id:id,
        width:"auto",
        messages:{
            address:{
                required:"Campo obrigatório"
            },
            description:{
                required:"Campo obrigatório"
            }
        },
        buttons:{
            cancel: {
                text:sandbox.msg("js.button.cancel"),
                click: function(ev){that.cancelButtonListener.call(that, ev);}
            },
            send:{
                text:sandbox.msg("js.button.send"),
                click: function(ev){that.saveButtonListener.call(that, ev);}
            }
        }
    });
    var el = dialog.el;

    var descField, charCounterEl;
    var MAX_DESC_SIZE = 300, charCounter, submitButtonEl = null;

    var init = false;

    $.extend(that, dialog, {
        show: function(address){
            if(address){
                el.find("#createIssueAddressField").val(address);
            }
            if(!init){
                init = true;
                $("#issueTypeRadio").buttonset();
            }
            $("#issueTypeRadio").buttonset("enable");
            dialog.show();
            if(submitButtonEl == null)
                submitButtonEl = $(el.parent().find("button").get(1));
            that.onKeypress();
        },

        initEvents: function(){
            charCounterEl = $("#createIssueCounter");
            descField = $("#createIssueDescField");
            /*
            descField.bind("keypress", function(){
                that.onKeypress.call(that);
            });*/
            descField.bind("keydown", function(){
                that.onKeypress.call(that);
            });
            el.find("a.addressMarker").click(function(){
                that.fireEvent("addressMarkerClick");
                that.hide();
            });
            this.onKeypress();
        },

        onKeypress: function(){
            charCounter = MAX_DESC_SIZE - descField.val().length;
            charCounterEl.html(charCounter);
            var isNegative = charCounter < 0;
            charCounterEl.toggleClass("negative", isNegative);
            if(submitButtonEl){
                submitButtonEl.attr("disabled", isNegative ? "true":null);
                submitButtonEl.attr("aria-disabled", isNegative ? "true":"false");
                submitButtonEl.toggleClass("ui-state-disabled", isNegative);
            }
        },
        
        saveButtonListener: function(ev){
            ev.preventDefault();
            this.onKeypress();
            if(charCounter <0){
                return;
            }
            
            this.resetForm();
            if(!this.validateForm())
                return;

            sandbox.resolveAddress($("#createIssueAddressField").val(), function(address){
               $("#createIssueAddressField").val(address.formatted);
               this.postRequest(address);
            }, that);
        },

        postRequest: function(addressObj){
            var formData = this.getFormData();
            
            this.wait(true);
            formData +=
                "&lat="+addressObj.lat +
                "&lng=" + addressObj.lng +
                "&city=" + addressObj.city +
                "&cep=" + addressObj.cep +
                "&neighborhood=" + addressObj.neighborhood +
                "&state=" + addressObj.state +
                "&number=" + addressObj.number +
                "&country=" + addressObj.country;
            
            sandbox.requestJson("POST", "issue/save", function(json){
                if(json.success){
                    this.hide();
                    this.fireEvent("createIssue", {result:json});
                } else {
                    this.wait(false);
                }
                
            }, this, formData );
        },

        cancelButtonListener: function(ev){
            this.hide();
        }
    });

    that.initEvents();
    return that;
}
