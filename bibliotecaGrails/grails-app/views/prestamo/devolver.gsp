<html>
	<head>
		<meta http-equiv="Content-Type content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main" />
		<title>Devoluci&oacute;n de material</title>
	</head>
	<body>
		<div class="body">
			<h1>Devoluci&oacute;n de material</h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:form method="post" >
				<div class="dialog">
				<table>
				<g:hiddenField name="id" value="${prestamoInstance.id}"/>
					<tbody>
						<tr class="prop">
							<td valign="top" class="name">
								<label for="material">Devoluci&oacute;n del material: ${prestamoInstance.materialPrestado}</label>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name">
								<label for="prestamo">Usuario: ${prestamoInstance.usuario}</label>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name">
								<label for="prestamo">Fecha de hoy: <g:formatDate format="dd/MM/yyyy HH:mm" date="${Calendar.getInstance().getTime()}" /></label>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name">
								<label for="prestamo">Fecha en la que deb&iacute;a devolverse: <g:formatDate format="dd/MM/yyyy HH:mm" date="${prestamoInstance.devolucion}" /></label>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
					<div class="buttons">
		                    <span class="button"><g:actionSubmit class="entrar" action="devolucion" value="Devolver" /></span>
		                    <span class="button"><g:actionSubmit class="entrar" action="recordar" value="Enviar recordatorio" /></span>
		             </div>
			</g:form>
		</div>
	</body>
</html>