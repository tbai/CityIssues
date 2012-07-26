/*
 * Should be used with the login/signInDialog template.
 * The id is "signInDialog".
 *
 * dependencies: mapUtils.js, bai.js
 */
bai.CommentDialog = function(sandbox){
    var id = "commentDialog";
    var that = {};
    
    var dialog = new bai.Dialog(sandbox, {
        id:id,
        width:"600px",
        draggable:false,
        position:["0px","0px"],
        buttons:{}
    });
    var el = dialog.el;

    var created = false;
    $.extend(that, dialog, {
        currentIssue:null,
        getCurrentIssue: function(){
            return that.currentIssue;
        },

        show: function(issue){
            that.currentIssue = issue;
            if(!created){
                that.initEvents();
            }
            el.html(
                '<div style="text-align:center; margin:auto;">'+
                '<iframe id="commentsFrame" name="comments" src="/public/comments/'+issue.id+'" frameborder="0" style="border:none; overflow:scroll; width:590px; height:500px;" allowTransparency="true"></iframe>'
                +'</div>'
            );
            // find dialog position
            
            dialog.show();
            
        },

        initEvents: function(){
            $(window).resize(that.resize);
            that.on("show", function(){
                el.dialog("option", "draggable", false);
                el.dialog("option", "position", [0,0]);
                $("html,body").css("overflow", "hidden");
                that.resize();
            }, that);
            that.on("hide", function(){
               $("body").css("overflow", "auto");
               sandbox.resize();
            });
        },

        resize: function(){            
            var incTop = $("#commentsFrame").offset().top;
            $("#commentsFrame").height( $(window).height() - incTop - 12);
        }
    });

    return that;
}