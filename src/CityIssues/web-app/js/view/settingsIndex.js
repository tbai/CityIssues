$(document).ready(function() {
    var self = this;
    var sandbox = new bai.Sandbox("settingsIndex");

    var forms = {};
    
    forms.password = new bai.Form(sandbox, {
        id:"passwordForm",
        rules:{
            password: {required: true, minlength: 6},
            confirmPassword: {required: true, equalTo: "#editPasswordField"}
        }
    });
    
    forms.user = new bai.Form(sandbox, {
        id:"userForm",
        rules:{
            name: {required: true, minlength: 2}
        }
    });
    
    for(var i in forms){
        forms[i].render();
    }

    var successDialog = $("#successDialog").dialog({
        autoOpen: false, modal:true, resizable:false, width:"500px",
        buttons: {
            /*gohome:{
                text:sandbox.msg("js.button.gohome"),
                click: function(ev){
                    window.location = "/";
                }
            },*/
            close: {
                text:sandbox.msg("js.button.ok"),
                click: function(ev){
                    $(this).dialog("close");
                }
            }
        }
    })

    $("#submitPasswordButton,#submitUserButton").button(
        {icons: {primary:'ui-icon-check'}}
    ).click(function(ev){
        ev.preventDefault();
        var formName = this.getAttribute("name");
        var form = forms[formName];
        if( !form.validate() )
            return false;

        var formData = form.getData(),
            formAction = form.getAction();
        
        sandbox.requestJson("POST", formAction, function(o){
            if(o.success){
                $("#successDialog").html(o.successMsg)
                successDialog.dialog("open");
                if(formName == "password"){
                    form.clear();
                }
            }
        }, self, formData );
        
        return true;
    });
});