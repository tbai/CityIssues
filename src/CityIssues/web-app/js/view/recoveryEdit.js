$(document).ready(function() {
    var self = this;
    var submitButton = $("#submitButton");
    submitButton.button({icons: {primary:'ui-icon-check'}});

    var sandbox = new bai.Sandbox("recoveryEdit");
    var form = bai.Form(sandbox, {
        id:"editPasswordForm",
        rules:{
            password: {required: true, minlength: 6},
            confirmPassword: {required: true, equalTo: "#editPasswordField"}
        }
    });

    form.render();

    submitButton.click(function(ev){
        if(form.validate()){
            form.submit();
            this.attr("disabled","true");
        }
    });
});