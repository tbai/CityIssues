<%
  def email = user.userInfo(name:"email");
  def name = user.userInfo(name:"name");
  
%>
<div class="header ui-state-default">
  <span class="ui-icon ui-icon-person"></span>
  <span class="text"><g:message code="settings.sub.user.title"/></span>
</div>
<div class="formContainer">
  <g:form id="userForm" class="dialogForm settings" name="user" method="post" url="${createLink(controller:'settings', action:'updateUser')}">
    <div class="field">
      <div class="fieldLabel"><label for="emailField"><g:message code="field.email.label"/>: </label> </div>
      <div class="fieldBody"><input id="emailField" type="text" name="email" class="textField ui-widget-content ui-corner-all required" disabled="true" value="${email}"/></div>
      <div class="fieldError ui-state-error" id="emailFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
    <div class="field">
      <div class="fieldLabel"><label for="nameField"><g:message code="field.name.label"/>: </label> </div>
      <div class="fieldBody"><input id="nameField" type="text" name="name" class="textField ui-widget-content ui-corner-all required" value="${name}"/></div>
      <div class="fieldError ui-state-error" id="nameFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
    <!--
    <div class="field">
      <div class="fieldLabel"><label for="surnameField"><g:message code="field.surname.label"/>: </label> </div>
      <div class="fieldBody"><input id="surnameField" type="text" name="surname" class="textField ui-widget-content ui-corner-all required" value=""/></div>
      <div class="fieldError ui-state-error" id="surnameFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
    -->
  </g:form>
  <div class="ft">
    <button name="user" id="submitUserButton"><g:message code="settings.sub.user.submit.label"/></button>
  </div>
</div>