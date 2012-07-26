<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <jawr:style src="/bundles/headerLinks.css" />
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <jawr:script src="/bundles/headerLinks.js" />
    <script type="text/javascript" src="${resource(dir:"/js/architecture", file:"mapUtils.js")}"></script>
    <script type="text/javascript" src="${resource(dir:"/js/view/admin", file:"parseEPTCRadars.js")}"></script>

    <style type="text/css">
      html{ font-size:90%; }
    </style>
    <title>Parse dos Radares da EPTC</title>

    <meta name="layout" content="basicHeader"/>
  </head>
  <body>
    <content tag="header-right">
      <g:render template="/shared/headerLinks" model="${[]}"/>
    </content>
    <h1>Parse dos Radares da EPTC</h1>
    <div>
     <label for="monthField">Mês: </label><input name="month" id="monthField" value="03"/>
    </div>
    <div>
      <label for="yearField">Ano: </label><input name="year" id="yearField" value="2011"/>
    </div>
    <div>
      <textarea value="" name="text" id="textField" cols="100" rows="10"></textarea>
    </div>
    <div>
      <button id="parseButton">Parse</button>
      <button id="submitButton" disabled="true">Enviar</button>
    </div>
    Result:
    <div id="result"></div>
  </body>
</html>
