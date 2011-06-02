<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main" />
		<resource:rating />
		<title>Material</title>
	</head>

	<body>
		<div class="body">
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:each in="${materialInstanceList}" status="i" var="materialInstance">
			<table>
				<tr class="prop">
					<td valign="top">
						<div class="material">
							<g:render template="/layouts/puntuacion" model="[material: materialInstance]" /><g:if test="${session?.usuario?.admin}"><p><g:link controller="${materialInstance.getClass().getSimpleName().toLowerCase()}" action="edit" id="${materialInstance.id}">[Editar material]</g:link></p></g:if>
							<g:render template="/layouts/${materialInstance.getClass().getSimpleName()}" model="[material: materialInstance]"/>
							<g:if test="${(materialInstance.comentarios != null) && (materialInstance.comentarios.size() > 0)}">
								<br></br><g:render template="/layouts/comentario" model="[comentarios: materialInstance.comentarios]"/>
							</g:if>
							<span class="material-comment"><g:comentarMaterial material="${materialInstance.id}"/></span>
						</div>
					</td>
				</tr>
			</table>
			<br></br>
			</g:each>
			<div class="paginateButtons">
				<g:paginate total="${materialInstanceTotal}" params="[q: params.q]" />
			</div>
		</div>
	</body>
</html>