<div id="feedbackDialog" title="Envie seu feedback">
  <form id="feedbackDialogForm" class="dialogForm" name="feedbackForm" method="POST" action="${createLink(controller:'public',action:'feedback')}">
    <div class="info"><g:message code="feedback.message"/></div>
    <div class="field ">
      <div class="fieldLabel"><label for="feedbackEmailField"><g:message code="field.yourEmail.label"/>: </label></div>
      <div class="fieldBody"><input id="feedbackEmailField" class="textField addressField ui-widget-content ui-corner-all required" type="text" name="email" value=""/></div>
      <div class="fieldError ui-state-error" id="feedbackEmailFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
    <div class="field">
      <div class="fieldLabel"><label for="feedbackTextField"><g:message code="field.feedback.label"/>: </label></div>
      <div class="fieldBody"><textarea id="feedbackTextField" rows="3" cols="20" class="ui-widget-content ui-corner-all required" name="text"></textarea></div>
      <div class="fieldError ui-state-error" id="feedbackTextFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
    </div>
  </form>
  <div class="resultMessage"></div>
</div>