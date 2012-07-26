

<%@ page import="com.cityissues.models.Broadcast" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'broadcast.label', default: 'Broadcast')}" />
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
            <g:hasErrors bean="${broadcastInstance}">
            <div class="errors">
                <g:renderErrors bean="${broadcastInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${broadcastInstance?.id}" />
                <g:hiddenField name="version" value="${broadcastInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="broadcast.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${broadcastInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="type"><g:message code="broadcast.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'type', 'errors')}">
                                    <g:select name="type" from="${broadcastInstance.constraints.type.inList}" value="${broadcastInstance?.type}" valueMessagePrefix="broadcast.type"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="issueTypeName"><g:message code="broadcast.issueTypeName.label" default="Issue Type Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'issueTypeName', 'errors')}">
                                    <g:textField name="issueTypeName" value="${broadcastInstance?.issueTypeName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="query"><g:message code="broadcast.query.label" default="Query" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'query', 'errors')}">
                                    <g:textField name="query" value="${broadcastInstance?.query}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="method"><g:message code="broadcast.method.label" default="Method" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'method', 'errors')}">
                                    <g:select name="method" from="${com.cityissues.models.BroadcastMethod?.values()}" keys="${com.cityissues.models.BroadcastMethod?.values()*.name()}" value="${broadcastInstance?.method?.name()}"  />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="maxResults"><g:message code="broadcast.maxResults.label" default="Max results" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'maxResults', 'errors')}">
                                    <g:textField name="maxResults" value="${broadcastInstance?.maxResults}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sinceId"><g:message code="broadcast.sinceId.label" default="Since Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'sinceId', 'errors')}">
                                    <g:textField name="sinceId" value="${broadcastInstance?.sinceId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="address"><g:message code="broadcast.address.label" default="Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'address', 'errors')}">
                                    <g:textField name="address" value="${broadcastInstance?.address?.formatted}"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lifeTime"><g:message code="broadcast.lifeTime.label" default="Life Time" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'lifeTime', 'errors')}">
                                    <g:textField name="lifeTime" value="${broadcastInstance?.lifeTime}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="updatePeriod"><g:message code="broadcast.updatePeriod.label" default="Update Period" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'updatePeriod', 'errors')}">
                                    <g:textField name="updatePeriod" value="${broadcastInstance?.updatePeriod}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="addressRegex"><g:message code="broadcast.addressRegex.label" default="Address Regex" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: broadcastInstance, field: 'addressRegex', 'errors')}">
                                    <g:textField name="addressRegex" value="${broadcastInstance?.addressRegex}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="test" action="test" value="${message(code: 'default.button.test.label', default: 'Test')}" /></span>
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="deleteIssues" value="${message(code: 'default.button.deleteIssues.label', default: 'Delete Issues')}" onclick="return confirm('${message(code: 'default.button.deleteIssues.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
