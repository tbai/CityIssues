
<%@ page import="com.cityissues.models.Issue" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      
        <content tag="header-right">
          <g:render template="/shared/headerLinks"/>
        </content>
        
        <g:render template="/shared/adminLinks"/>
        
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <!--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>-->
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: issueInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.type.label" default="Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="issueType" action="show" id="${issueInstance?.type?.id}">${issueInstance?.type?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: issueInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.address.label" default="Address" /></td>
                            
                            <td valign="top" class="value"><g:link controller="address" action="show" id="${issueInstance?.address?.id}">${issueInstance?.address?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.totalVotes.label" default="Total Votes" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: issueInstance, field: "totalVotes")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.dateResolved.label" default="Date Resolved" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${issueInstance?.dateResolved}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.dateStart.label" default="Date Start" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${issueInstance?.dateStart}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.dateFinish.label" default="Date Finish" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${issueInstance?.dateFinish}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: issueInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${issueInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.scheduled.label" default="Scheduled" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${issueInstance?.scheduled}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.status.label" default="Status" /></td>
                            
                            <td valign="top" class="value">${issueInstance?.status?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.user.label" default="User" /></td>
                            
                            <td valign="top" class="value"><g:link controller="adminUser" action="show" id="${issueInstance?.user?.id}">${issueInstance?.user?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="issue.votes.label" default="Votes" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${issueInstance.votes}" var="v">
                                    <li><g:link controller="issueVote" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${issueInstance?.id}" />
                    <!--<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>-->
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
