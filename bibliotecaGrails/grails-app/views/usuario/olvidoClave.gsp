<html>
	<head>
		<meta http-equiv="Content-Type content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main" />
		<title>Reiniciar password</title>
	</head>
	<body>
		<div class="body">
			<h1>Reiniciar password</h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:form method="post" name="reiniciarPassword">
				<div class="dialog">
					<table>
						<tbody>
							<tr class="prop">
								<p>&iquest;Olvidaste tu clave?	Ingres&aacute; el e-mail con el que te registraste para que te enviemos una nueva.</p>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for='email'>E-mail: </label>
								</td>
								<td valign="top">
									<input type='text' name='email' id='email' />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<g:actionSubmit class="button" action="reiniciarPassword" value="Reiniciar password"/>
			</g:form>
		</div>
	</body>
</html>