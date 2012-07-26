package com.cityissues.taglibs
import com.cityissues.models.*

class IssueTagLib {
    static namespace = "bai"
    
    def issueService
    def springSecurityService

    def printDescription = { attrs, body ->
        def issue = (attrs.issue instanceof Issue) ?: Issue.get(attrs.issue)
        
        if(issue?.description?.size() >0 ){
            def desc = issue.description.encodeAsHTML()
            desc = desc.replaceAll(/http(s)?:\/\/[^ ]+/, "<a target=\"_blank\" href=\"\$0\">\$0</a>")
            out << desc
        } else {
            out << ""
        }
    }
    
    def isIssueOwner = {attrs, body->
        if( springSecurityService.isLoggedIn() ){
            def issue = (attrs.issue instanceof Issue) ?: Issue.get(attrs.issue)
            def isOwner = issue?.user?.username?.equals(springSecurityService?.principal?.username)
            if(isOwner){
                out << body()
            }
        }
    }

    def currentVote = { attrs, body->
        def vote = issueService.getVote(attrs.issue)
        def result = "none"
        if(vote){
            result = vote.positive ? "positive":"negative"
        }

        out << result
    }

    def issueTypeRadio = { attrs, body->
        out << "<div id=\"${attrs.id?:'issueTypeRadio'}\" class=\"${attrs.className?:'issueTypeRadio'}\">"
        def issueTypeList = IssueType.findAllByCanCreateAndIsOpenData(true, false).sort{ 
            if( it.name.equals("info") ){
                "0"
            } else if(it.description && it.description.size()){
                it.description
            } else {
                message(code:'issueType.'+it.name+'.label') 
            }
        };
        
        issueTypeList.each{
            def type = it.name
            def selected = type.equals("info") ? " checked=\"true\"" : ""
            out << "<input id=\"issueType-$type\" class=\"issueType issueType-$type\" type=\"radio\" name=\"issueType\" value=\"${type}\" $selected></input>"
            out << "<label title=\"${it.description ?: message(code:'issueType.'+type+'.label')}\" for=\"issueType-$type\"><img src=\"/images/markers/${type}.png\"/></label>"
        }
        out << "</div>"
    }

    def issueTypeCheckbox = { attrs, body->
        def checkedList = attrs.checked?.split(",")       
        def id = attrs.id?:'issueTypeCheckbox'
        out << "<div id=\"${id}\" class=\"${attrs.className?:'issueTypeCheckbox'}\">"
        
        ["info", "transportation","trafficcamera", "crime", "restaurants", "entertainment"].each{ type->
            def selected = (type in checkedList) || checkedList?.getAt(0)?.equals("all") ? ' checked="true"' : ""
            out << "<input id=\"$id-$type\" class=\"issueType issueType-$type\" type=\"checkbox\" name=\"$type\" $selected></input>"
            out << "<label title=\"${message(code:'issueType.'+type+'.label')}\" for=\"$id-$type\" class=\"issueType-$type\"><span class=\"icon\">&nbsp;</span></label>"
        }
        out << "</div>"
    }
    def issueTypeSelect = { attrs, body->
        def checked = attrs.checked ?: "all"
        def id = attrs.id?:'issueTypeSelect'
        out << "<div id=\"${id}\" class=\"${id} selectButton ${attrs.className?:''}\">"
        out << "<button id=\"${id}Button\" class=\"issueType issueType-$checked\">${message(code:'issueType.'+checked+'.label')}</button>"
        out << "<div id=\"${id}Menu\" class=\"selectButtonMenu ui-state-active ui-corner-tl ui-corner-bottom\">"
        out << "<ul>"
        def selected = false, msg = "", inputId = ""
        ["all", "info", "transportation","trafficcamera", "crime", "restaurants", "entertainment"].each{ type->
            inputId = id+ "aInput-"+type
            out << "<li>"
            selected = type.equals(checked)
            msg = message(code:'issueType.'+type+'.label')
            out << "<input id=\"$inputId\" class=\"issueType issueType-${type}\" type=\"radio\" name=\"issueType\" value=\"${type}\" ${selected?'checked="true"':''}></input>"
            out << "<label title=\"${msg}\" for=\"$inputId\" class=\"${type.equals('all')?'first':''} ui-corner-all issueType-$type${selected?' selected':''}\" itclass=\"issueType-$type\">${msg}</label>"
            out << "</li>"
        }
        out << "</ul>"
        out << "</div>"
        out << "</div>"
    }
    
    def issueDateSelect = { attrs, body->
        def checked = attrs.checked ?: "year"
        def id = attrs.id?:'issueDateSelect'
        out << "<div id=\"${id}\" class=\"selectButton ${attrs.className?:''}\">"
        out << "<button id=\"${id}Button\" class=\"\">${message(code:'issueDate.'+checked+'.label')}</button>"
        out << "<div id=\"${id}Menu\" class=\"selectButtonMenu ui-state-active ui-corner-tl ui-corner-bottom\">"
        out << "<ul>"
        def selected = false, msg = "", inputId = ""
        ["day", "month", "year"].each{ type->
            inputId = id+ "Input-"+type
            out << "<li>"
            selected = type.equals(checked)
            msg = message(code:'issueDate.'+type+'.label')
            out << "<input id=\"$inputId\" class=\"\" type=\"radio\" name=\"issueDate\" value=\"${type}\" ${selected?'checked="true"':''}></input>"
            out << "<label title=\"${msg}\" for=\"$inputId\" class=\"${type.equals('day')?'first':''} ui-corner-all issueDate-$type${selected?' selected':''}\">${msg}</label>"
            out << "</li>"
        }
        out << "</ul>"
        out << "</div>"
        out << "</div>"
    }

    /* vote using session
    def currentVote = { attrs, body->
        def vote = issueService.getVote(attrs.issue)
        def result = "none"
        if(vote){
            result = vote > 0 ? "positive":"negative"
        }

        out << result
    }
    */
}
