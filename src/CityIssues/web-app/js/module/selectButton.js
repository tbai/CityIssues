bai.module.SelectButton = function(id, map, sandbox){
    var that = new bai.Module(id),
        el = that.el;
        
    var CLASS_SELECTED = "selected",
        CLASS_HOVER = "hover";
        
    var defaultClassName = el[0].className;
        
    var button = el.find("button"),
        buttonTextEl,
        menu = el.find( "div.selectButtonMenu" ),
        labels = menu.find("label");
        
    button.button({icons: {
        primary:map.icon,
        secondary: "ui-icon-triangle-1-s"
    }});

    buttonTextEl = button.find("span.ui-button-text");

    $.extend(that, {
        initEvents: function(){
            button.click(function(ev){
                ev.preventDefault();
                that.showMenu();
            });
            
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
               
            // hide the menu after 1s if inactive

            menu.hover(
                //handlerIn
                function(ev){
                    menu.canHide = false;
                }, 
                // handlerOut
                function(ev){
                    menu.canHide = true;
                }
            );

            labels.click(function(ev){
                that.hideMenu();
                labels.removeClass("selected");
                $(this).addClass("selected");
                el[0].className = defaultClassName + " " + $(this).attr("itclass");
                buttonTextEl.html($(this).attr("title"));
            });
        },
        
        showMenu: function(){
            that.cancelMenuTimer();
            menu.addClass("active");
            button.addClass("active ui-corner-top ui-state-active");
            button.removeClass("ui-corner-all");
            button.css("border-bottom", "none");
            
            menu.canHide = true;
            that.startMenuTimer();
        },
        
        hideMenu: function(){
            that.cancelMenuTimer();
            menu.removeClass("active");
            button.addClass("ui-corner-all");
            button.removeClass("active ui-corner-top ui-state-active");
        },
        
        startMenuTimer: function(){
            menu.timer = setTimeout(function(){
                if(menu.canHide){
                    that.hideMenu();
                } else {
                    that.startMenuTimer();
                }
            }, 1000);
        },
        
        cancelMenuTimer: function(){
            if(menu.timer){
                clearTimeout(menu.timer);
            }
        }
    });
    
    that.initEvents();
    
    return that;
};
