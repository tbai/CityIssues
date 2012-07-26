<%
  def selectedClass = "ui-tabs-selected ui-state-active";
  def controllerName = params.controller;
  def isSelected = { className -> params.controller.equals(className) ? selectedClass : '' };
%>
<div class="ui-tabs ui-widget ui-corner-all">
  <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
    <li class="ui-state-default ui-corner-top ${isSelected('adminUser')}">
      <a href="${createLink(controller: 'adminUser', action:'list')}">
        <g:message code="users.label"/>
      </a>
    </li>
    <li class="ui-state-default ui-corner-top ${isSelected('adminIssue')}">
      <a href="${createLink(controller: 'adminIssue', action:'list')}"><g:message code="issues.label"/></a>
    </li>
    <li class="ui-state-default ui-corner-top ${isSelected('broadcast')}">
      <a href="${createLink(controller: 'broadcast', action:'list')}"><g:message code="broadcasts.label"/></a>
    </li>
  </ul>
</div>
