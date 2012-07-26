bai.module.ShortcutsView = function(id, sandbox){
    var that = new bai.Module(id),
        el = that.el;
        
    var CLASS_SELECTED = "selected",
        CLASS_HOVER = "hover";
        
        
    var labels = el.find("label");
    
    $.extend(that, {
        init:function(){
            that.initEvents();
        },

        initEvents: function(){
            
            labels.hover(
                //handlerIn
                function(ev){
                    $(this).addClass(CLASS_HOVER);
                }, 
                // handlerOut
                function(ev){
                    $(this).removeClass(CLASS_HOVER);
                }
            );

            labels.click(function(ev){
                //var formEl = $(this).closest
                var cl = $(this).closest("ul").attr("class");

                $(this).closest("form").find("ul."+cl+" label").removeClass("selected");
                $(this).addClass("selected");
            });
        }
    });
    
    that.init();
    
    return that;
};
