<div id="signUpDialog" title="${message(code:'signUp.dialogTitle')}">
  <form id="signUpDialogForm" class="dialogForm" name="signUpDialogForm" action='${request.contextPath}/user/save'>
    <div class="info"><g:message code="signUp.message"/></div>
    <div class="field ">
      <div class="fieldLabel"><label for="signUpNameField"><g:message code="field.name.label"/>: </label></div>
      <div class="fieldBody"><input id="signUpNameField" type="text" name="name" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
      <div class="fieldError ui-state-error" id="signUpNameFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
    <div class="field ">
      <div class="fieldLabel"><label for="signUpEmailField"><g:message code="field.email.label"/>: </label></div>
      <div class="fieldBody"><input id="signUpEmailField" type="text" name="email" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
      <div class="fieldError ui-state-error" id="signUpEmailFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
    <div class="field">
      <div class="fieldLabel"><label for="signUpPasswordField"><g:message code="signUp.password.label"/>: </label> </div>
      <div class="fieldBody"><input id="signUpPasswordField" type="password" name="password" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
      <div class="fieldError ui-state-error" id="signUpPasswordFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
    <div class="field">
      <div class="fieldLabel"><label for="signUpConfirmPasswordField"><g:message code="signUp.confirmPassword.label"/>: </label> </div>
      <div class="fieldBody"><input id="signUpConfirmPasswordField" type="password" name="confirmPassword" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
      <div class="fieldError ui-state-error" id="signUpConfirmPasswordFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
    <div class="field">
        <div class="fieldBody">
          <input id="signUpTermsField" type="checkbox" name="terms"/>
          <% def termsLink = link(controller:'public', action:'termsOfUse', target:'_blank'){message(code:'link.termsOfUse')}; %>
          <label for="signUpTermsField" style="margin-left:5px;"><g:message code="signUp.terms.label" args="${[termsLink]}"/></label>
        </div>
        <div class="fieldError ui-state-error" id="signUpTermsFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
  </form>
  <div class="resultMessage"></div>
</div>
