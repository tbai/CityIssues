<div class="header ui-state-default">
  <span class="ui-icon ui-icon-unlocked"></span>
  <span class="text"><g:message code="recovery.edit.h1"/></span>
</div>
<div class="formContainer">
  <g:form id="passwordForm" class="dialogForm settings" name="password" method="post" url="${createLink(controller:'settings', action:'updatePassword')}">
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
  <div class="ft">
    <button name="password" id="submitPasswordButton"><g:message code="settings.sub.password.submit.label"/></button>
  </div>
</div>