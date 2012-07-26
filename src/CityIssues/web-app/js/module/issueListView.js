bai.module.IssueListView = function(id, sandbox){
    var that = new bai.Module(id),
        el = that.el;
    var body = el.find("#issueListBody"),
        sortByForm = el.find("#issueListSortByForm");

    var SELECTED_CLASS = "selected",
        MOUSEOVER_CLASS = "ui-state-hover mouseover";

    var selectedIssueEl = null,
        overIssueEl = null;
        
    var isEmpty = true;
        
    var canMakeRequestTest = true;
    $.extend(that, {
        init: function(){
            el.find("#issueListSortBy").buttonset();
            el.delegate(" div.issueListItem", "mouseover", function(ev){
                var target = ev.currentTarget;
                if(selectedIssueEl != target && !$(target).hasClass("mouseover")){
                    $(target).addClass(MOUSEOVER_CLASS);
                    overIssueEl = target;
                    setTimeout(function(){
                        if($(overIssueEl).hasClass(MOUSEOVER_CLASS)){
                            that.fireEvent("mouseoverIssue", {issueId:that.getIssueId(overIssueEl)});
                        }
                    }, 500);
                }
            });

            el.delegate("div.issueListItem", "mouseout", function(ev){
                var target = ev.currentTarget;
                if(selectedIssueEl != target && !$(ev.relatedTarget).closest("#" + target.id).length && $(target).hasClass("mouseover")){
                    $(target).removeClass(MOUSEOVER_CLASS);
                    that.fireEvent("mouseoutIssue", {issueId:that.getIssueId(target)});
                }
            });

            el.delegate("div.issueListItem", "click", function(ev){
                var target = ev.currentTarget;
                if(selectedIssueEl != target){
                    $(selectedIssueEl).removeClass(SELECTED_CLASS);
                    selectedIssueEl = target;
                    $(selectedIssueEl).removeClass(MOUSEOVER_CLASS);
                    $(selectedIssueEl).addClass(SELECTED_CLASS);
                    that.fireEvent("clickIssue", {issueId:that.getIssueId(target)});
                }
            });
            
            body.bind("scroll", that.makeRequestTest);

            sortByForm.change(function(){
                that.fireEvent("sortBy", {value:that.getSortByValue()});
            });
            
            
        },
        
        /**
         * Test scroll position if need to load more issues 
         */
        makeRequestTest: function(){
            if(!canMakeRequestTest) return;
            
            
            var lastChild = body.find("div.issueListItem:last");                
            if(lastChild && lastChild.length == 1){
                lastChild = $(lastChild[0]);
                var lastChildTop = lastChild.offset().top;
                var bodyBottom = body.offset().top + body.height();
                if(lastChildTop <= bodyBottom){
                    that.fireEvent("requestIssues");
                    canMakeRequestTest = false;
                    setTimeout(function(){
                        canMakeRequestTest = true;
                    }, 500);
                }
            }
        },

        getSortByValue: function(){
            return sortByForm.serializeArray()[0].value;
        },

        highlight: function(issue, scrollIntoView){
            that.unselectAll();
            var itemEl = body.find("#il_" + that.getItemId(issue));
            itemEl.removeClass(MOUSEOVER_CLASS);
            itemEl.addClass(SELECTED_CLASS);
            selectedIssueEl = itemEl.get(0);
            if(scrollIntoView){
                if(itemEl.get(0)){
                    itemEl.get(0).scrollIntoView();
                }
            }
        },

        getItemId: function(issue){
            return issue.id;
        },

        appendIssueHtml: function(html, issue){
            if(body.find("div.textMessage").length >0){
                body.html("");
            }
            if(that.getSortByValue() == "date"){
                // since new items will always have the first date
                body.prepend(html);
            } else {
                // since new items will always have 0 votes
                body.append(html);
            }
        },

        updateIssueHtml: function(issueId, html){
            el.find("#il_" + issueId).replaceWith(html);
        },

        renderIssues: function(html, appendBefore){
            // remove all text messages, they can exist when there is no issues in a certain region
            body.find("div.issueListMessage").remove();
            
            // find ids and remove old divs
            var div = document.createElement("div");
            div.innerHTML = html;
            $(div).css("backgroundColor", "#9FC");
            
            if(!isEmpty && $(div).find("div.issueListMessage").length >0 ){
                return;
            }
            // remove duplicated elements
            $(div).find("div.issueListItem").each( function(){
                var removeEl = body.find("#" + this.id);
                if(removeEl){
                    if(removeEl.hasClass("selected")){
                        $(this).addClass("selected");
                    }
                    removeEl.remove();
                }
            });
            
            if(appendBefore){
                body.prepend(div);
            } else {
                body.append(html);
            }
            // animate the new elements
            $(div).animate({ backgroundColor: "transparent" }, 1000);
            
            // test if we need to make a new request according with the scroll position
            that.makeRequestTest();
            isEmpty = false;
        },
        
        clear: function(){
            canMakeRequestTest = true;
            selectedIssueEl = null;
            overIssueEl = null;
            body.html("");
            isEmpty = true;
        },

        getIssueId: function(issueEl){
            var issueId = issueEl.id;
            return issueId.substring(issueId.indexOf("_")+1, issueId.length);
        },
        
        removeIssue: function(issueId){
            el.find("#il_" + issueId).remove();
        },

        unselectAll: function(){
            var items = el.find("div.issueListItem");
            $(items).each(function(i, el){
                $(el).removeClass(SELECTED_CLASS);
                $(el).removeClass(MOUSEOVER_CLASS);
                selectedIssueEl = null;
            });
        }
    });

    that.init();
    return that;
};