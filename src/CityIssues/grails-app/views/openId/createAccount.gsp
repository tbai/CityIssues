<head>
<meta name='layout' content='main'/>
<title>Suricato Urbano - Criar conta</title>
</head>

<body>

<div class='body'>

	<h4>No user was found with that OpenID but you can register now and associate your OpenID with that account.</h4>

	<br/>

	<g:if test='${openId}'>
	Or if you're already a user you can <g:link action='linkAccount'>link this OpenID</g:link> to your account<br/>
	<br/>
	</g:if>

	<g:hasErrors bean="${command}">
	<div class="errors">
		<g:renderErrors bean="${command}" as="list"/>
	</div>
	</g:hasErrors>

	<g:if test='${flash.error}'>
	<div class="errors">${flash.error}</div>
	</g:if>

	<g:form action='createAccount'>

		<table>
		<tr>
			<td>Open ID:</td>
			<td><span id='openid'>${openId}</span></td>
		</tr>

		<tr>
			<td><label for='displayUsername'>Email:</label></td>
			<td><g:textField name='displayUsername' value='${command.email}'/>
              <input type="hidden" name='username' value='${command.email}'/>
            </td>
			
		</tr>

		<tr>
			<td><label for='password'>Senha:</label></td>
			<td><g:passwordField name='password' value='${command.password}'/></td>
		</tr>

		<tr>
			<td><label for='password2'>Confirme sua senha:</label></td>
			<td><g:passwordField name='password2' value='${command.password2}'/></td>
		</tr>

		</table>

		<input type='submit' value='Register'/>

	</g:form>
</div>

</body>
