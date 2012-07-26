
<%@ page import="com.cityissues.models.Broadcast" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'broadcast.label', default: 'Broadcast')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      
        <content tag="header-right">
          <g:render template="/shared/headerLinks"/>
        </content>
        
        <g:render template="/shared/adminLinks"/>
        
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
                            <td valign="top" class="name"><g:message code="broadcast.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.type.label" default="Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "type")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.issueTypeName.label" default="Issue Type Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "issueTypeName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.query.label" default="Query" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "query")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.method.label" default="Method" /></td>
                            
                            <td valign="top" class="value">${broadcastInstance?.method?.encodeAsHTML()}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.maxResults.label" default="Max results" /></td>
                            
                            <td valign="top" class="value">${broadcastInstance?.maxResults?.encodeAsHTML()}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${broadcastInstance?.lastUpdated}" /></td>
                            
                        </tr>
                                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.sinceId.label" default="Since Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "sinceId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.address.label" default="Address" /></td>
                            
                            <td valign="top" class="value"><g:link controller="address" action="show" id="${broadcastInstance?.address?.id}">${broadcastInstance?.address?.formatted}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.lifeTime.label" default="Life Time" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "lifeTime")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.updatePeriod.label" default="Update Period" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "updatePeriod")}</td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.addressRegex.label" default="Address Regex" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: broadcastInstance, field: "addressRegex")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="broadcast.issues.label" default="Issues" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${broadcastInstance.issues}" var="issue">
                                    <li><g:link controller="adminIssue" action="show" id="${issue?.id}">${issue?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${broadcastInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
