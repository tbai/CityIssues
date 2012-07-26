/*
 * Base for all dialogs
 *
 * dependencies: bai.js
 */

/**
 * @param sandbox
 * @param map Configuration map with the following options:
 *  id:
 *  width:
 *  buttons:
 *  messages (optional):
 *  create (optional):
 */
bai.Dialog = function(sandbox, map){

    var id = map.id;
    var that = new bai.Module(id);
    var el = that.el;

    var isCreated = false;

    $.extend(that, {
        show: function(){
            that.wait(false);
            that.fireEvent("beforeShow");
            if(!isCreated){
                isCreated = true;
                var options = {autoOpen: false, modal:true, width:map.width, resizable:false,
                    buttons:map.buttons, close:that.onClose, open:that.onOpen}
                if( map.create ){
                    options.create = map.create;
                }
                if(map.position){
                    options.position = map.position;
                }
                el.dialog(options);
                var validatorOptions = {
                    debug:true,
                    errorClass: 'invalid',
                    validClass: '',
                    errorPlacement: function(error, element) {
                        $('#' + element.attr('id') + 'Error label').remove();
                        $('#' + element.attr('id') + 'Error span.text').append(error);
                        $(element).closest('.field').addClass("invalid");
                    },
                    highlight: function( element, errorClass, validClass ) {
                        $(element).closest('.field').addClass("invalid");
                    },
                    unhighlight: function( element, errorClass, validClass ) {
                        $(element).closest('.field').removeClass("invalid");
                    }
                }
                if(map.messages){
                    validatorOptions.messages = map.messages;
                }
                if(map.rules){
                    validatorOptions.rules = map.rules;
                }
                that.validator = null;
                if(el.find("form").get(0)){
                    that.validator = el.find("form").validate(validatorOptions);
                }
                that.fireEvent("create");
            }
            
            el.dialog("open");
        },

        /**
         * Set the values for a group of fields
         * @params fields <JSON Object> {fieldName:val, ...}
         */
        setValues: function(fields){
            var form = el.find("form");
            for(var n in fields){
                form.find('input[name="' + n + '"]').val(fields[n]);
            }
        },
        
        setValue: function(name, value){
            var o = {};
            o[name] = value;
            that.setValues(o);
        },

        /**
         * Validate the form values using the validator rules
         * @return <Boolean>
         */
        validateForm: function(){
            return that.validator.form();
        },


        /**
         * Creates the encoded data do be posted
         */
        getFormData: function(){
            return el.find("form").serialize();
        },
        
        getValue: function(name){
            var form = el.find("form");
            var list = form.serializeArray();
            for(var n in list){
                if(list[n].name == name){
                    return list[n].value;
                }
            }
            return null;
        },

        /**
         *
         * @param wait
         */
        wait:function(wait){
            // disable all or enable all fields
            var selector = "button,input,select,textarea,label";
            var parentEl = $(el.parent().get(0));
            var elList = parentEl.find(selector);
            if(wait){
                // wait

                // disable the close button
                el.dialog( "option", "closeOnEscape", false );
                parentEl.find("a.ui-dialog-titlebar-close").css("visibility", "hidden");
                // disable all fields and add the 'wait' style
                parentEl.find("button").addClass("ui-state-disabled");
                elList.attr("disabled", true);
                elList.attr("aria-disabled", true);
                parentEl.addClass("wait");
                //el.dialog( "option", "disabled", true );
            } else {
                // continue

                // enable the close button
                el.dialog( "option", "closeOnEscape", true );
                parentEl.find("a.ui-dialog-titlebar-close").css("visibility", "visible");
                // enable all fields and remove the 'wait' style
                parentEl.find("button").removeClass("ui-state-disabled");                
                elList.removeClass("ui-state-disabled");
                elList.removeClass("ui-button-disabled");
                elList.removeAttr("disabled");
                elList.attr("aria-disabled", false);
                parentEl.removeClass("wait");
            }
        },

        clear: function(){
            if(!that.validator) return;
            
            try{
                that.validator.currentForm.reset();
            }catch(e){console.log(e);}
        },

        resetForm: function(){
            if(!that.validator) return;
            that.clearErrors();
        },

        clearErrors: function(){
            el.find("div.fieldError label").remove();
            el.find("div.field").removeClass("invalid");
        },

        /**
         * Render error messages
         * @param map <JSON Object> {fieldName:"error message", ...}
         */
        showErrors: function(map){
            try{
                that.validator.showErrors(map);
            }catch(e){}
        },

        onOpen: function(){
            that.fireEvent("show");
        },

        onClose: function(){
            that.fireEvent("beforeHide");
            that.resetForm();
            that.clear();
            that.fireEvent("hide");
        },

        hide: function(){
            el.dialog("close");
        }
    });

    return that;
}