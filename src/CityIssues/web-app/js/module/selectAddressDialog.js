/*
 * Should be used with the public/selectAddressDialog template.
 * The id is "selectAddressDialog".
 *
 * dependencies:  bai.js
 */
bai.SelectAddressDialog = function(sandbox){
    var me = this;
    var id = "selectAddressDialog";
    var that = new bai.Module(id);
    var el = that.el;

    var SELECTED_CLASS = "ui-state-active selected",
        MOUSEOVER_CLASS = "ui-state-hover mouseover";

    var isCreated = false;

    $.extend(that, {
        show: function(list, callbackFn, scope){
            that.fireEvent("beforeShow");

            if(!isCreated){
                isCreated = true;
                el.dialog({autoOpen: false, modal:true, width:"550px",
                    buttons:{
                        "Selecionar": that.okButtonListener
                    }
                });
                el.delegate("div.item", "mouseover", function(ev){
                    if(!$(ev.target).hasClass("selected"))
                        $(ev.target).addClass(MOUSEOVER_CLASS);
                });
                el.delegate("div.item", "mouseout", function(ev){
                    $(ev.target).removeClass(MOUSEOVER_CLASS);
                });
                el.delegate("div.item", "click", function(ev){
                    el.find("div.body").children(".selected").each(function(i){
                        $(this).removeClass(SELECTED_CLASS);
                    });
                    $(ev.target).addClass(SELECTED_CLASS);
                });
            }

            // render html
            var html = '<div class="body">';
            var selected, address;
            for(var i=0; i<list.length; i++){
                selected = i==0 ? " " + SELECTED_CLASS : "";
                address = list[i].formatted_address ? list[i].formatted_address : list[i];
                html += '<div class="ui-state-default item'+selected+'" index="'+i+'">'+address+'</div>';
            }
            html += '</div>';
            el.html(html);
            that.callback = {
                fn:callbackFn,
                scope:scope
            }
            
            el.dialog("open");
        },
        hide: function(){
            this.fireEvent("beforeHide");
            el.dialog("close");
        },
        okButtonListener: function(ev){
            ev.preventDefault();

            var selectedEl = $("#" + id + " div.item.selected");
            var selectedIndex = 0;
            if(selectedEl){
                selectedIndex = parseInt(selectedEl.attr("index"));
            }
            that.hide();
            try{
                that.callback.fn.call(that.callback.scope, selectedIndex);
            }catch(e){}
        }
    });

    return that;
}