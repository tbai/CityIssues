
<%@ page import="com.cityissues.models.IssueType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'issueType.label', default: 'IssueType')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'issueType.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="file" title="${message(code: 'issueType.file.label', default: 'File')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'issueType.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'issueType.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="showShortcut" title="${message(code: 'issueType.showShortcut.label', default: 'Show Shortcut')}" />
                        
                            <g:sortableColumn property="canCreate" title="${message(code: 'issueType.canCreate.label', default: 'Can Create')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${issueTypeInstanceList}" status="i" var="issueTypeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${issueTypeInstance.id}">${fieldValue(bean: issueTypeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: issueTypeInstance, field: "file")}</td>
                        
                            <td>${fieldValue(bean: issueTypeInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: issueTypeInstance, field: "description")}</td>
                        
                            <td><g:formatBoolean boolean="${issueTypeInstance.showShortcut}" /></td>
                        
                            <td><g:formatBoolean boolean="${issueTypeInstance.canCreate}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${issueTypeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
