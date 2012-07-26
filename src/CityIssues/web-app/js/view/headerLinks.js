$(document).ready(function() {
    var sandbox = new bai.Sandbox("headerLinks");
    
    $("#headerlinkSignIn").click(function(ev){
        sandbox.showSignIn();
    });
    $("#headerlinkSignUp").click(function(ev){
        sandbox.showSignUp();
    });
    $("#headerlinkFeedback").live("click", function(ev){
        sandbox.showFeedback();
    });

    // show browser warning
    
});
