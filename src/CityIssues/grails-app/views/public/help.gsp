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

    <jawr:style src="/bundles/publicHelp.css" />
    <bai:jsMessages filter="js"/>
    <jawr:script src="/bundles/headerLinks.js" />
    
    <meta name="layout" content="basicHeader"/>
  </head>
  <body class="publicHelp">
    <content tag="header-right">
      <g:render template="/shared/headerLinks"/>
    </content>
    <div class="header-title ui-widget ui-widget-header ui-corner-all">
      <h1><g:message code="info.whatIsThis.title"/></h1>
    </div>
    <div class="content">
      <div class="block ui-widget ui-widget-content ui-corner-all" style="padding:2px; border:none; ">
       
        <div class="body ui-widget ui-widget-content ui-corner-all" style="border-color:#ccc;">
          <img src="/images/help/screens.jpg" style="float:left; margin:-2em 2em 0 0;"/>
          <p><g:message code="info.whatIs.text.1"/></p>
          <p><g:message code="info.whatIs.text.2"/></p>
          <p><g:message code="info.whatIs.text.3"/></p>
          <div class="clear"></div>
        </div>
        
        
        <div class="subtitle">
          <img src="${resource(dir:"/images/help")}/help-subtitle-signup.png"></img>
        </div>
        <div class="list">
          <ol>
            <li><g:message code="public.help.guide.signup.list.1"/></li>
            <li><g:message code="public.help.guide.signup.list.2"/></li>
            <li><g:message code="public.help.guide.signup.list.3"/></li>
            <li><g:message code="public.help.guide.signup.list.4"/></li>
          </ol>
        </div>
        <div class="guide">
          <img src="${resource(dir:"/images/help")}/help-guide-signup.png?v=${bai.appVersion()}"></img>
        </div>
        
        <div class="subtitle">
          <img src="${resource(dir:"/images/help")}/help-subtitle-navigation.png"></img>
        </div>
        <div class="list">
          <ol>
            <li><g:message code="public.help.guide.navigation.list.1"/></li>
            <li><g:message code="public.help.guide.navigation.list.2"/></li>
            <li><g:message code="public.help.guide.navigation.list.3"/></li>
            <li><g:message code="public.help.guide.navigation.list.4"/></li>
          </ol>
        </div>
        <div class="guide">
          <img src="${resource(dir:"/images/help")}/help-guide-navigation.png?v=${bai.appVersion()}"></img>
        </div>
        
        <div class="subtitle">
          <img src="${resource(dir:"/images/help")}/help-subtitle-alert.png"></img>
        </div>
        <div class="list">
          <ol>
            <li><g:message code="public.help.guide.alert.list.1"/></li>
            <li><g:message code="public.help.guide.alert.list.2"/></li>
            <li><g:message code="public.help.guide.alert.list.3"/></li>
            <li><g:message code="public.help.guide.alert.list.4"/></li>
          </ol>
        </div>
        <div class="guide">
          <img src="${resource(dir:"/images/help")}/help-guide-alert.png?v=${bai.appVersion()}"></img>
        </div>
        <!--
        <div class="header ui-state-default">
          <span class="ui-icon ui-icon-info"></span>
          <span class="text"><g:message code="info.whatIs.title"/></span>
        </div>
        <div class="body">
          <img src="/images/help/screens.jpg" style="float:left; margin:-2em 10px 2em 0;"/>
          <p><g:message code="info.whatIs.text.1"/></p>
          <p><g:message code="info.whatIs.text.2"/></p>
        </div>
        <div class="clear"></div>
        <div class="header ui-state-default">
          <span class="ui-icon ui-icon-video"></span>
          <span class="text"><g:message code="info.howItWorks.title"/></span>
        </div>
        <div class="body" style="text-align:center;">
          <iframe title="YouTube video player" width="480" height="390" src="http://www.youtube.com/embed/zGR0bAeP350" frameborder="0" allowfullscreen></iframe>
        </div>
        -->
        <div style="margin-top:1em;">
          <script type="text/javascript"><!--
          google_ad_client = "ca-pub-6509899034752146";
          /* HelpBottom */
          google_ad_slot = "2831684658";
          google_ad_width = 300;
          google_ad_height = 250;
          //-->
          </script>
          <script type="text/javascript"
          src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
          </script>
        </div>
      </div>
      <div class="clear"></div>
    </div>
  </body>
</html>
