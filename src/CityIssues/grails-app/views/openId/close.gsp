<head>
<title>Close</title>
<style type='text/css' media='screen'>
</style>
</head>

<body>
  <script type="text/javascript">
      var result = ${json};
      //alert((window.location+'').split('?')[1]);
      window.opener.handleOpenIDResponse(result);
      window.close();
  </script>
</body>
