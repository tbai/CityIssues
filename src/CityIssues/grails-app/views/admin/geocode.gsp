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
    <script type="text/javascript" src="${resource(dir:"/js/admin", file:"geocode.js")}"></script>

    <style type="text/css">
      html{ font-size:90%; }
    </style>
    <title>Geocode</title>

    <meta name="layout" content="basicHeader"/>
  </head>
  <body>
    <content tag="header-right">
      <g:render template="/shared/headerLinks" model="${[]}"/>
    </content>
    <h1>Geocode</h1>
    <div>
      <textarea type="text" value="" name="address" id="addressField" cols="100" rows="10"></textarea>
    </div>
    <div>
      <input type="text" size="100" name="address-comp" value="" id="addressCompField"/>
    </div>
    <div>
      <button id="submitButton">Geocode</button>
    </div>
    Result:
    <div id="result"></div>
  </body>
</html>
