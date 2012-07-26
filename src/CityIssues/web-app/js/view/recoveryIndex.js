$(document).ready(function() {
    var submitButton = $("#submitButton");
    submitButton.button({icons: {primary:'ui-icon-check'}});

    var sandbox = new bai.Sandbox("recoveryIndex");
    var form = bai.Form(sandbox, {
        id:"recoverPasswordForm",
        rules:{
            email: {required: true, email:true}
        }
    });

    form.render();

    submitButton.click(function(ev){
        if(form.validate()){
            $(this).attr("disabled","true");
            form.submit();
        }       
    });
});