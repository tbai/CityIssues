<% def rootDir = resource(dir:"/"); %>
<html>
  <head>
    
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title><g:message code="app.title"/></title>

    <link rel="stylesheet" type="text/css" href="${rootDir}js/yui_3.2.0/cssgrids/grids-min.css">

    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0px; padding: 0px }      
    </style>

    <jawr:style src="/bundles/settingsIndex.css" />
    <bai:jsMessages filter="js"/>
    <jawr:script src="/bundles/settingsIndex.js" />
    <meta name="layout" content="basicHeader"/>
  </head>
  <body class="settingsIndex">
    <content tag="header-right">
      <g:render template="/shared/headerLinks"/>
    </content>
    <div class="ui-widget ui-widget-header ui-corner-all" style="padding:10px;">
      <g:message code="link.settings"/>
    </div>
    <div class="content">
      <div class="block ui-widget ui-widget-content ui-corner-all" style="padding:2px;">
        <g:render template="/settings/user"/>     
        <g:render template="/settings/password"/>
      </div>
      <div class="clear"></div>
    </div>
    <div id="successDialog" title="">

    </div>
  </body>
</html>
