

<%@ page import="com.cityissues.models.Broadcast" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'broadcast.label', default: 'Broadcast')}" />
        <title><g:message code="default.test.label" args="[entityName]" /></title>
    </head>
    <body>
      
        <content tag="header-right">
          <g:render template="/shared/headerLinks"/>
        </content>
        
        <g:render template="/shared/adminLinks"/>
        
        <div class="nav">
            <span class="menuButton"><g:link class="back" onclick="javascript: history.go(-1)"><g:message code="default.back.label" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.test.label" args="[entityName]" /></h1>
            <div style="margin-left:1em; font-size: 12px;">
              <g:each in="${results}" var="result" status="i" >
                <div><b>${i+1} - </b>${result.text}</div>
                <div><b>Address list: </b><span style="color:red;">${result.addressList}</span></div>
                <br/>
              </g:each>
            </div>
        </div>
    </body>
</html>
