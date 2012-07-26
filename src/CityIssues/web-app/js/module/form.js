/*
 * Should be used with the issue/createDialog template.
 * The id is "createIssueDialog".
 *
 * dependencies: mapUtils.js, bai.js
 */

/**
 * @param sandbox
 * @param map Configuration map with the following required options:
 *  id:
 *  width:
 *  buttons:
 *  messages:
 *  create (optional):
 */
bai.Form = function(sandbox, map){
    var id = map.id;
    var that = new bai.Module(id);
    var form = that.el;

    $.extend(that, {
        render: function(){
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
            that.validator = form.validate(validatorOptions);
        },

        /**
         * @params fields <Array>
         *
         */
        setValues: function(fields){
            for(var n in fields){
                form.find('input[name="' + n + '"]').val(fields[n]);
            }
        },

        validate: function(){
            return that.validator.form();
        },

        getData: function(){
            return form.serialize();
        },

        getAction: function(){
            return form.attr("action");
        },

        clear: function(){
            try{
                that.validator.currentForm.reset();
            }catch(e){console.log(e);}
        },

        reset: function(){
            if(!that.validator) return;
            that.clearErrors();
        },

        clearErrors: function(){
            form.find("div.fieldError label").remove();
            form.find("div.field").removeClass("invalid");
        },

        showErrors: function(map){
            try{
                that.validator.showErrors(map);
            }catch(e){}
        },

        enable: function(){
            form.find("input,select,textarea").attr("disabled","true")
        },

        disable: function(){
            form.find("input,select,textarea").attr("disabled",null)
        },

        submit:function(){
            form.get(0).submit();
        }
    });

    return that;
}