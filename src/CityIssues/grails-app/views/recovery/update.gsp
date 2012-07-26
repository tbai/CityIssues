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
    <jawr:script src="/bundles/headerLinks.js" />

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
        <h2><g:message code="recovery.update.h1"/></h2>
        <p><g:message code="recovery.update.p1" args="${[(link(url:'#', name:'signIn'){message(code:'link.clickHere')})]}"/></p>
    </div>
    <script type="text/javascript">
      $(document).ready(function() {
        var self = this;
        $('#headerlinkSignIn').unbind("click");
        var sandbox = new bai.Sandbox("recoveryUpdate");
        $('a[name="signIn"]').click(function(ev){
          sandbox.getDialog("signIn").show(function(result){
            if(result.success){
              window.location = "/";
            }
          }, self);

        });
      });
    </script>
  </body>
</html>
