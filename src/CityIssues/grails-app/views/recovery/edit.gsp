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
    <jawr:script src="/bundles/recoveryEdit.js" />

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
      <g:form id="editPasswordForm" class="dialogForm" name="editPassword" method="post" url="${[controller:'recovery',action:'update', id:token]}">
        <h2><g:message code="recovery.edit.h1"/></h2>
        <br/>
        <p><g:message code="recovery.edit.p1"/></p>
        <div class="field">
          <div class="fieldLabel"><label for="editPasswordField"><g:message code="signUp.password.label"/>: </label> </div>
          <div class="fieldBody"><input id="editPasswordField" type="password" name="password" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
          <div class="fieldError ui-state-error" id="editPasswordFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
        </div>
        <div class="field">
          <div class="fieldLabel"><label for="editConfirmPasswordField"><g:message code="signUp.confirmPassword.label"/>: </label> </div>
          <div class="fieldBody"><input id="editConfirmPasswordField" type="password" name="confirmPassword" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
          <div class="fieldError ui-state-error" id="editConfirmPasswordFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
        </div>
        
      </g:form>
      <button name="submit" id="submitButton">Salvar nova senha</button>
    </div>
  </body>
</html>
