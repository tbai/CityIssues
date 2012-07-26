<%@page import="com.cityissues.models.IssueType"%>
<% def rootDir = resource(dir:"/"); %>
<html>
  <head>
    
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title><g:message code="meta.title"/></title>

    <link rel="stylesheet" type="text/css" href="${rootDir}js/yui_3.2.0/cssgrids/grids-min.css">

    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0px; padding: 0px }
    </style>

    <jawr:style src="/bundles/publicIndex.css" />
    <!--[if lte IE 8]>
      <link href="${resource(dir:"css/view/", file:"publicIndexIE.css")}" rel="stylesheet" type="text/css">
    <![endif]-->
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry,adsense"></script>
    <bai:jsMessages filter="js"/>
    <script src="http://code.google.com/apis/gears/gears_init.js" type="text/javascript"></script>
    <script src="${resource(dir:'/js/geo-location', file:'geo.js')}" type="text/javascript" ></script>
    <jawr:script src="/bundles/publicIndex.js" />

    <script type="text/javascript" src="https://apis.google.com/js/plusone.js"></script>
    
    
    <meta name="layout" content="basicHeader"/>
  </head>
  <body class="home loading">
    <content tag="header-top">
      <bai:isNonSupportedBrowser>
        <div class="ui-state-error topWarning">
          <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span><strong>Atenção, navegador desatualizado.</strong>
          O seu navegador não é suportado por todas as tecnologias deste site. Por favor, atualize para um navegador moderno. <a href="http://www.whatbrowser.org/pt-br/browser/" target="_blank">Clique aqui</a> para saber mais.
        </div>
      </bai:isNonSupportedBrowser>
    </content>
    <content tag="header-left">
      <a class="ui-corner-all" style="padding:1px 10px; text-align:center; font-weight:bold; color:#2E6E9E;"
         href="${createLink(controller:'public', action:'help')}" target="_blank" id="link-whatIsThis" ><g:message code="info.whatIsThis.title"/></a>
    </content>
    <content tag="header-right">
      <g:render template="/shared/headerLinks" model="${[]}"/>
    </content>
 
    <div id="location-container" class="ui-widget-header ui-corner-top">
      
      <div class="right-container">
        <div id="plusone-main"class="block first-right">
          
        </div>
        <!--
        <div class="block first-right">
          <form id="fdForm" name="fdForm" method="POST" action="#">
            <%def filterDate = params.fd ?: "year";%>
            <bai:issueDateSelect id="issueDateSelect" checked="${filterDate}"/>
            </form>
        </div>
        <div class="block">
          <form id="ftForm" name="ftForm" method="POST" action="#">
            <%def typesChecked = params.ft ?: "all";%>          
            <bai:issueTypeSelect id="issueTypeSelect" checked="${typesChecked}"/>
          </form>
        </div>
        <div class="clear"></div>-->
      </div>
      <div>
        <div id="addressBarContainer" style="float:left;">
          <label for="address" class="block"><g:message code="issue.field.address.title"/> </label>
          <div class="addressField block ui-widget-content ui-corner-left">
            <a href="#" id="addressBarMarkerLink" class="block addressMarker" title="${message(code:'public.addressBar.marker.title')}"></a>
            <input class="block" type="text" name="address" id="addressField" value=""/>
          </div>
          <button class="block" name="search" id="searchButton">Ir</button><label style="margin:0 5px;">::</label>
          <div class="clear"></div>
        </div>
        <div style="float:left; text-align:left;"> <button name="report" id="reportButton"><g:message code="createIssue.title"/></button></div>
        <div class="clear"></div>
      </div>
      <div class="clear"></div>
    </div>
    
    <div id="body-container" class="ui-widget-content loading">
        <div id="shortcuts-container" class="ui-widget-content">
          <g:render template="/public/shortcuts"/>
        </div>
        <div id="list-container" class="ui-widget-content">
          <%def issueListModel = [issues:[]];%>
          <g:render template="/issue/list" model="${issueListModel}"/>
        </div>
        <div id="map-container" class="ui-widget-content">          
          <div id="map_canvas"></div>
          <!--<div id="addsline">
          
          </div>-->
        </div>
    </div>
    <div id="footer" class="ui-widget-header">
      <footer>
      v <bai:appVersion/>
      <div class="right"><a href="mailto:tiagoxbai@gmail.com">Tiago Bai</a> / <a href="http://www.polistecnologia.com.br/" target="_blank">Polis Tecnologia</a> <!--<a href="#" id="whantToHelpLink">quer ajudar?</a>--></div>
      <div class="clear"></div>
      </footer>
    </div>
    <g:render template="/public/dialogs"/>
    <script type="text/javascript">
      var _appVersion = "${bai.appVersion()}";
      var _addr = ${params.addr ? "\""+params.addr+"\"" : "false"};
      var _zoom = ${params.z ? params.z : "false"};
      <% def typeList = IssueType.list().collect{it.name}.join('","'); %>
      var _issueTypeList = ["${typeList}"];
      <g:if test="${geoIpLocation}">
      _geoIpLocation = ${geoIpLocation};
      </g:if>
    </script>
  </body>
</html>
