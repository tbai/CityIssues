<%@ page import="com.cityissues.models.Issue" %>
<%@ page import="com.cityissues.models.IssueType" %>
<%@ page import="com.cityissues.models.Address" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'issue.label', default: 'Issue')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        
        <filterpane:includes />
    </head>
    <body>
        <content tag="header-right">
          <g:render template="/shared/headerLinks"/>
        </content>
        
        <g:render template="/shared/adminLinks"/>
        
        <div class="nav">
          
        </div>
        
        <div class="body">
          
            <h1><g:message code="issue.list.label" default="Lista de alertas" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'issue.id.label', default: 'Id')}" />
                        
                            <th><g:message code="issue.type.label" default="Categoria" /></th>
                        
                            <g:sortableColumn property="description" title="${message(code: 'issue.description.label', default: 'Descrição')}" />
                            
                            <th><g:message code="address.city.label" default="Cidade" /></th>
                            
                            <th><g:message code="address.state.label" default="Estado" /></th>
                        
                            <g:sortableColumn property="totalVotes" title="${message(code: 'issue.totalVotes.label', default: 'Votos')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'issue.dateCreated.label', default: 'Data de Criação')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${issueInstanceList}" status="i" var="issueInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${issueInstance.id}">${fieldValue(bean: issueInstance, field: "id")}</g:link></td>
                        
                            <td><g:message code="issueType.${fieldValue(bean: issueInstance, field:'type.name')}.label"/></td>
                        
                            <td width="30%">${fieldValue(bean: issueInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: issueInstance, field: "address.city")}</td>
                            
                            <td>${fieldValue(bean: issueInstance, field: "address.state")}</td>
                        
                            <td>${fieldValue(bean: issueInstance, field: "totalVotes")}</td>
                        
                            <td><g:formatDate date="${issueInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${issueInstanceTotal == null ? Issue.count(): issueInstanceTotal}" params="${filterParams}" />
                <filterpane:filterButton text="Filtrar" />
            </div>
             
        </div>
      
        <%
          def addressList = Address.list();
          def addressCityList = addressList*.city.unique() - ["null", null];
          def addressStateList = addressList*.state.unique() - ["null", null];
          def addressNeighborhoodList = addressList*.neighborhood.unique() - ["null", null];
          
          def typeValues = IssueType.list()*.name.collect{  [id:it,name:message(code:"issueType.${it}.label")] };
          
          def filterPropertyValues = [
              'type.name':[values:typeValues,optionKey:'id',optionValue:"name"], 
              'address.city':[values:addressCityList], 
              'address.neighborhood':[values:addressNeighborhoodList], 
              'address.state':[values:addressStateList]];
        %>
      
        <filterpane:filterPane domain="${Issue}" title="Filtrar" dialog="True" 
                               dateFormat="dd-MM-yyyy"
                               associatedProperties="type.name,address.city,address.neighborhood,address.state"
                               filterProperties="${['dateCreated', 'type', 'totalVotes', 'address', 'description']}"
                               filterPropertyValues="${filterPropertyValues}"
                               />
    </body>
</html>
