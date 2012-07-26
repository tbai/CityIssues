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

    <jawr:style src="/bundles/recoveryIndex.css" />
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <bai:jsMessages filter="js"/>
    <jawr:script src="/bundles/recoveryIndex.js" />

    <meta name="layout" content="basicHeader"/>
  </head>
  <body class="recoveryIndex">
    <content tag="header-left">
      <a class="ui-state-highlight ui-corner-all" style="padding:1em;" href="#" id="link-whatIsThis" ><g:message code="info.whatIsThis.title"/></a>
    </content>
    <content tag="header-right">
      <g:render template="/shared/headerLinks"/>
    </content>

    <div class="formContainer">
        <br/><br/>
        <h2><g:message code="recovery.invalidToken.h1"/></h2>
        <p><g:message code="recovery.invalidToken.p1"/></p>
        <p><g:message code="recovery.invalidToken.p2" args="${[(link(controller:'recovery', action:'index'){message(code:'link.ClickHere')})]}"/></p>
    </div>
  </body>
</html>
