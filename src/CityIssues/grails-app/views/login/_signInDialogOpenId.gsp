<%
  def useOpenId = false;
  //
%>
<div id="signInDialog" title="${message(code:'link.signIn')}">  
  <form id="signInDialogForm" method="POST" target="openid_popup" class="dialogForm${useOpenId?' openid':''}"  name="openIdLoginForm" action="/j_spring_openid_security_check">
    <div class="block">
      <div class="field ">
        <div class="fieldLabel"><label for="signInEmailField"><g:message code="field.email.label"/>: </label></div>
        <div class="fieldBody"><input id="signInEmailField" type="text" name="j_username" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
        <div class="fieldError ui-corner-all ui-state-error" id="signInEmailFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
      </div>
      <g:if test="${useOpenId}">
        <div class="field haspass">
          <input type="hidden" name="openid" value=""/>
          <div><p>Você tem uma senha do Suricato Urbano?</p></div>            
          <div class="fieldBody" style="font-weight:normal; color:black;">
            <div style="margin-bottom:5px;">
              <input type="radio" id="signInHasPassFalse" name="haspass" value="false"/>
              <label class="fieldLabel" for="signInHasPassFalse"> Não, me ajude a fazer login.</label>
            </div>
            <div>
              <input type="radio" id="signInHasPassTrue" name="haspass" checked value="true"/>
              <label class="fieldLabel" for="signInHasPassTrue"> Sim, eu tenho uma senha:</label>
            </div>
          </div>
        </div>
      </g:if>
      <div class="field">
        <g:if test="${!useOpenId}">
        <div class="fieldLabel"><label for="signInPasswordField"><g:message code="field.password.label"/>: </label> </div>
        </g:if>
        <div class="fieldBody"><input id="signInPasswordField" type="password" name="j_password" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
        <a id="signInForgotPassword" target="_blank" href="${createLink(controller:'recovery', action:'index')}" class="smallLink"><g:message code="link.forgotPassword"/></a>
        <div class="fieldError ui-state-error" id="signInPasswordFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
      </div>
      <div class="field">
        <div class="fieldBody">
          <input type="checkbox" id="signInRememberMeField" name="_spring_security_remember_me" ${remembered ? 'checked' : ''}/><label class="fieldLabel" for="signInRememberMe">&nbsp; <g:message code="field.rememberMe.label"/></label>
        </div>
        <div class="fieldError ui-state-error" id="signInRememberMeFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
      </div>
    </div>
  </form>
  <div class="footer ui-state-highlight">
    <span class="strong"><g:message code="signIn.newUser.label"/></span><br/>
    <a id="signInSignUpLink" href="#"><g:message code="signIn.newUser.link"/></a>
  </div>
</div>