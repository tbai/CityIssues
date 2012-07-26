
<%@ page import="com.cityissues.models.Broadcast" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'broadcast.label', default: 'Broadcast')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
      
        <content tag="header-right">
          <g:render template="/shared/headerLinks"/>
        </content>
        
        <g:render template="/shared/adminLinks"/>
        
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'broadcast.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'broadcast.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="type" title="${message(code: 'broadcast.type.label', default: 'Type')}" />
                        
                            <g:sortableColumn property="issueTypeName" title="${message(code: 'broadcast.issueTypeName.label', default: 'Issue Type Name')}" />
                        
                            <g:sortableColumn property="query" title="${message(code: 'broadcast.query.label', default: 'Query')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'broadcast.lastUpdated.label', default: 'Last Updated')}" />
                            
                            <th>${message(code: 'broadcast.issues.label', default: 'Issues')}</th>
                            
                            
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${broadcastInstanceList}" status="i" var="broadcastInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${broadcastInstance.id}">${fieldValue(bean: broadcastInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: broadcastInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: broadcastInstance, field: "type")}</td>
                        
                            <td>${fieldValue(bean: broadcastInstance, field: "issueTypeName")}</td>
                        
                            <td>${fieldValue(bean: broadcastInstance, field: "query")}</td>
                        
                            <td><g:formatDate date="${broadcastInstance.lastUpdated}" /></td>
                            
                            <td>${broadcastInstance.issues.size()}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${broadcastInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
