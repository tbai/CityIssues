<div style="display:none;">
  <g:render template="/issue/createDialog"/>
  <g:render template="/public/selectAddressDialog"/>

  <div id="commentDialog" title="Comentários"></div>
  <% // show dialog %>
  <g:if test="${showDialog}">
    <script type="text/javascript">
      var _showDialog = "${showDialog}";
      var _fields = {};
      <g:each in="${fields}" var="field">
        _fields["${field.name}"] = "${field.value}";
      </g:each>
    </script>
  </g:if>
  <% // show issue %>
  <g:if test="${params?.i}">
    <script type="text/javascript">
      var _showIssue = "${params.i}";
    </script>
  </g:if>
  <% // store logged user info %>
  <script type="text/javascript">
    var _user = {};
    <sec:ifLoggedIn>
    _user.email = "${sec.loggedInUserInfo(field:'username')}";
    </sec:ifLoggedIn>
  </script>
</div>