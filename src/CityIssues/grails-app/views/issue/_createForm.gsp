<form id="formIssueCreate" class="dialogForm" name="formIssueCreate" action="">
  <div class="field ">
    <div class="fieldBody"><bai:issueTypeRadio/></div>
    <div class="fieldError ui-state-error"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
  </div>
  <div class="field ">
    <div class="fieldLabel"><label for="address"><g:message code="issue.field.address.title"/> </label></div>    
    <div class="fieldBody addressField ui-widget-content ui-corner-all">
      <a href="#" class="block addressMarker" title="${message(code:'public.addressBar.marker.title')}"></a>
      <input id="createIssueAddressField" class="textField addressField required" type="text" name="address" value=""/>
      <div class="clear"></div>
    </div>
    <div class="fieldDesc"><g:message code="issue.field.address.desc"/></div>
    <div class="fieldError ui-state-error" id="createIssueAddressFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
  </div>
  <div class="field">
    <div id="createIssueCounter"></div>
    <div class="fieldLabel"><label for="description"><g:message code="issue.field.description.title"/> </label></div>
    <div class="fieldBody"><textarea id="createIssueDescField" rows="3" cols="20" class="ui-widget-content ui-corner-all required" name="description"></textarea></div>
    <div class="fieldError ui-state-error" id="createIssueDescFieldError"><span class="ui-icon ui-icon-alert"></span><span class="text"></span><span class="clear"></span></div>
  </div>
</form>