<?xml version="1.0" encoding="UTF-8"?>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="pt-BR">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    
    <!-- google_ad_section_start -->
    <g:each var="metaName" in="${['description', 'title', 'author', 'language', 'keywords']}">
      <% def msgCode = "meta.${metaName}"; %>
      <meta name="${metaName}" content="${message(code:msgCode)}">
    </g:each>
    <!-- google_ad_section_end -->

    <noscript><meta HTTP-EQUIV="Refresh" CONTENT="0;URL=/public/noJavaScript"></meta></noscript>

    <title>
      <g:layoutTitle default="${message(code:'main.title')}" />
    </title>
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href="${resource(dir: 'images', file: 'favicon.ico')}"/>

    <jawr:style src="/bundles/layoutBasicHeader.css" />
    
    <g:layoutHead />    
  </head>
  <body class="${pageProperty(name:'body.class')}" style="${pageProperty(name:'body.style')}">
    <g:pageProperty name="page.header-top"/>
    <div id="layoutHeader" style="background:url(/images/logo.png) no-repeat 0 0; height:55px;">
      <div class="" style="text-align:right; height:auto;" id="headerLinksContainer"><g:pageProperty name="page.header-right"/></div>
      <div style="margin-left:500px;">
        <g:pageProperty name="page.header-left"/>
      </div>
    </div>
    <div class="mainBody" style="">
      <!-- google_ad_section_start -->
      <g:layoutBody />
      <!-- google_ad_section_end -->
    </div>
    <script type="text/javascript">
      var _gaq = _gaq || [];
      _gaq.push(['_setAccount', 'UA-7152361-2']);
      _gaq.push(['_trackPageview']);
      (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();
    </script>
  </body>
</html>
