<html>
	<head>
		<meta http-equiv="Content-Type content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main" />
		<title>Login</title>
	</head>
	<body>
		<div class="body">
			<h1>Login</h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off' name="auth">
				<div class="dialog">
					<table>
						<tbody>
							<tr class="prop">
								<td valign="top" class="name">
									<label for='password'>Usuario: </label>
								</td>
								<td valign="top">
									<input type='text' name='j_username' id='username' />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for='password'>Password: </label>
								</td>
								<td valign="top">
									<input type='password' name='j_password' id='password' />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for='remember_me'>Recordarme: </label>
								</td>
								<td valign="top">
									<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
									<g:if test='${hasCookie}'>checked='checked'</g:if> />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<g:actionSubmit class="button" value="Entrar" /> <g:link controller="usuario" action="olvidoClave">&iquest;Olvidaste tu clave?</g:link>
			</form>			
		</div>
	</body>
</html>