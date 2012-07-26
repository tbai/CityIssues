<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title><g:message code="comments.title"/></title>
    <meta property="fb:app_id" content="${bai.config(key:'facebook.apid')}"/>
    
  </head>
  <body>
    <div id="fb-root"></div>
    <script src="http://connect.facebook.net/pt_BR/all.js#xfbml=1"></script>

    <fb:comments href="${bai.serverURL()}?i=${params.id}"></fb:comments>
  </body>
</html>
