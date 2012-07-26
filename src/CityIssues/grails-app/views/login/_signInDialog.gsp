<div id="signInDialog" title="${message(code:'link.signIn')}">  
  <form id="signInDialogForm" method="POST" target="openid_popup" class="dialogForm"  name="openIdLoginForm" action="/j_spring_openid_security_check">
    <input type="hidden" name="openid" value=""/>
    <div class="block">
      <div class="field ">
        <div class="fieldLabel"><label for="signInEmailField"><g:message code="field.email.label"/>: </label></div>
        <div class="fieldBody"><input id="signInEmailField" type="text" name="j_username" class="textField addressField ui-widget-content ui-corner-all required" value=""/></div>
        <div class="fieldError ui-corner-all ui-state-error" id="signInEmailFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
      </div>      
      <div class="field">
        <div class="fieldLabel"><label for="signInPasswordField"><g:message code="field.password.label"/>: </label> </div>
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
  <div class="footer" >
    <span class="strong"><g:message code="signIn.newUser.label"/></span>
    <a id="signInSignUpLink" href="#"><g:message code="signIn.newUser.link"/></a>
    <br/>
    <span>ou</span>
    <div class="loginWithGoogle">
      <span>Faça login usando  </span> 
      <button id="loginWithGoogleButton" >Google</button>
    </div>
  </div>
</div>