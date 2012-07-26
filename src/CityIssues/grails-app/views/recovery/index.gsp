<% def rootDir = resource(dir:"/"); %>
<html>
  <head>
    
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title><g:message code="app.title"/></title>

    <link rel="stylesheet" type="text/css" href="${rootDir}js/yui_3.2.0/cssgrids/grids-min.css">

    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0px; padding: 0px }      
    </style>

    <jawr:style src="/bundles/recoveryIndex.css" />
    <bai:jsMessages filter="js"/>
    <jawr:script src="/bundles/recoveryIndex.js" />

    <meta name="layout" content="basicHeader"/>
  </head>
  <body class="recoveryIndex">
    <content tag="header-left">
      <a class="ui-state-highlight ui-corner-all" style="padding:1em;" href="#" id="link-whatIsThis" ><g:message code="info.whatIsThis.title"/></a>
    </content>
    <content tag="header-right">
      <g:render template="/shared/headerLinks"/>
    </content>

    <div class="formContainer">
      <g:form id="recoverPasswordForm" class="dialogForm" name="recoverPassword" method="post" url="[controller:'recovery',action:'password']">
        <h2><g:message code="recovery.index.h1"/></h2>
        <br/>
        <p><g:message code="recovery.index.p1"/></p>
        <div class="field ${flash?.errorMessage?.size() ? ' invalid':''}">
          <div class="fieldLabel"><label for="recoverPasswordEmailField"><g:message code="recovery.email.label"/></label></div>
          <div class="fieldBody"><input id="recoverPasswordEmailField" type="text" name="email" class="textField addressField ui-widget-content ui-corner-all required" value="${flash.email}"/></div>
          <div class="fieldError ui-corner-all ui-state-error " id="recoverPasswordEmailFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text">${flash?.errorMessage}</span><span class="clear"></span></div>
        </div>        
      </g:form>
      <button name="submit" id="submitButton">Enviar Instruções</button>
    </div>
  </body>
</html>
