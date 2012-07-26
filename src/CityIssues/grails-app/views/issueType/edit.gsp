

<%@ page import="com.cityissues.models.IssueType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'issueType.label', default: 'IssueType')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${issueTypeInstance}">
            <div class="errors">
                <g:renderErrors bean="${issueTypeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${issueTypeInstance?.id}" />
                <g:hiddenField name="version" value="${issueTypeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="file"><g:message code="issueType.file.label" default="File" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueTypeInstance, field: 'file', 'errors')}">
                                    <g:textField name="file" value="${issueTypeInstance?.file}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="issueType.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueTypeInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${issueTypeInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="issueType.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueTypeInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${issueTypeInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="showShortcut"><g:message code="issueType.showShortcut.label" default="Show Shortcut" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueTypeInstance, field: 'showShortcut', 'errors')}">
                                    <g:checkBox name="showShortcut" value="${issueTypeInstance?.showShortcut}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="canCreate"><g:message code="issueType.canCreate.label" default="Can Create" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueTypeInstance, field: 'canCreate', 'errors')}">
                                    <g:checkBox name="canCreate" value="${issueTypeInstance?.canCreate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="issues"><g:message code="issueType.issues.label" default="Issues" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: issueTypeInstance, field: 'issues', 'errors')}">
                                    
<ul>
<g:each in="${issueTypeInstance?.issues?}" var="i">
    <li><g:link controller="issue" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="issue" action="create" params="['issueType.id': issueTypeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'issue.label', default: 'Issue')])}</g:link>

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
