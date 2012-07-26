
<div class="header-links ${className?:''}">
  <nav>
  <sec:ifLoggedIn>
    <span class="email">
      <a class="email" ><user:userInfo name="name"/></a>
      <span class="separator">|</span>
    </span>
  </sec:ifLoggedIn>

  <span class="home">
    <g:link controller="public" action="index"><g:message code="link.home"/></g:link>
    <span class="separator">|</span>
  </span>
  <span class="feedback">
    <a href="#" id="headerlinkFeedback" ><g:message code="link.feedback"/></a>
    <span class="separator">|</span>
  </span>
  <sec:ifAnyGranted roles="ROLE_ADMIN">
    <g:link controller='adminUser'><g:message code="link.admin"/></g:link>
    <span class="separator">|</span>
  </sec:ifAnyGranted>
  <sec:ifLoggedIn>
    <g:link controller='settings' action="index"><g:message code="link.settings"/></g:link>
    <span class="separator">|</span>
    <g:link controller='logout'><g:message code="link.signOut"/></g:link>
  </sec:ifLoggedIn>
  <sec:ifNotLoggedIn>
    <a href="#" id="headerlinkSignIn" name="signIn"><g:message code="link.signIn"/></a>
    <span class="separator">|</span>
    <a href="#" id="headerlinkSignUp" name="signUp"><g:message code="link.signUp"/></a>
  </sec:ifNotLoggedIn>
  </nav>
</div>
<div style="display:none;">
  <g:render template="/login/signInDialog"/>
  <g:render template="/login/signUpDialog"/>
  <g:render template="/public/feedbackDialog"/>
</div>
