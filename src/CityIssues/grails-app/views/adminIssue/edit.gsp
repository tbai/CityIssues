

<%@ page import="com.cityissues.models.Issue" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      
        <content tag="header-right">
          <g:render template="/shared/headerLinks"/>
        </content>
        
        <g:render template="/shared/adminLinks"/>
        
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${issueInstance}">
            <div class="errors">
                <g:renderErrors bean="${issueInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${issueInstance?.id}" />
                <g:hiddenField name="version" value="${issueInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="type"><g:message code="issue.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'type', 'errors')}">
                                    <g:select name="type.id" from="${com.cityissues.models.IssueType.list()}" optionKey="id" value="${issueInstance?.type?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="issue.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" cols="40" rows="5" value="${issueInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="address"><g:message code="issue.address.label" default="Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'address', 'errors')}">
                                    <g:select name="address.id" from="${com.cityissues.models.Address.list()}" optionKey="id" value="${issueInstance?.address?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="totalVotes"><g:message code="issue.totalVotes.label" default="Total Votes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'totalVotes', 'errors')}">
                                    <g:textField name="totalVotes" value="${fieldValue(bean: issueInstance, field: 'totalVotes')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateResolved"><g:message code="issue.dateResolved.label" default="Date Resolved" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'dateResolved', 'errors')}">
                                    <g:datePicker name="dateResolved" precision="day" value="${issueInstance?.dateResolved}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateStart"><g:message code="issue.dateStart.label" default="Date Start" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'dateStart', 'errors')}">
                                    <g:datePicker name="dateStart" precision="day" value="${issueInstance?.dateStart}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateFinish"><g:message code="issue.dateFinish.label" default="Date Finish" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'dateFinish', 'errors')}">
                                    <g:datePicker name="dateFinish" precision="day" value="${issueInstance?.dateFinish}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="issue.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${issueInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="scheduled"><g:message code="issue.scheduled.label" default="Scheduled" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'scheduled', 'errors')}">
                                    <g:checkBox name="scheduled" value="${issueInstance?.scheduled}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="status"><g:message code="issue.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'status', 'errors')}">
                                    <g:select name="status" from="${com.cityissues.model.IssueStatus?.values()}" keys="${com.cityissues.model.IssueStatus?.values()*.name()}" value="${issueInstance?.status?.name()}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="user"><g:message code="issue.user.label" default="User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'user', 'errors')}">
                                    <g:select name="user.id" from="${com.cityissues.models.User.list()}" optionKey="id" value="${issueInstance?.user?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="votes"><g:message code="issue.votes.label" default="Votes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueInstance, field: 'votes', 'errors')}">
                                    
<ul>
<g:each in="${issueInstance?.votes?}" var="v">
    <li><g:link controller="issueVote" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="issueVote" action="create" params="['issue.id': issueInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'issueVote.label', default: 'IssueVote')])}</g:link>

                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
