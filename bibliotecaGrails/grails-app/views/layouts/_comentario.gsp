<span class="material-comments">Comentarios: </span>
<table>
	<tbody>
		<tr class="prop">
			<td valign="top">
				<g:each in="${comentarios}" status="j" var="comentarioInstance"> 
				<table>
					<tbody>
							<tr class="prop">
								<td valign="top">
									<span class="material-comment">Comentario: </span>
									<span class="valor">${comentarioInstance.comentario.encodeAsHTML()}</span>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top">
									<span class="material-comment">Autor: </span>
									<span class="valor">${comentarioInstance.autor.username}</span>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top">
									<span class="material-comment">Fecha: </span>
									<span class="valor">${comentarioInstance.dateCreated.format("dd MMMM, yyyy, hh:mm")}</span>
								</td>
							</tr>
							<g:if test="${comentarioInstance.lastUpdated.after(comentarioInstance.dateCreated)}" >
								<tr class="prop">
									<td valign="top">
										<span class="material-comment">Editado: </span>
										<span class="valor">${comentarioInstance.lastUpdated.format("dd MMMM, yyyy, hh:mm")}</span>
									</td>
								</tr>
							</g:if>
							<span class="material-comment"><g:borrarOEditarComentario comentario="${comentarioInstance.id}"/></span>
						</tbody>
					</table>
					<br></br>
				</g:each>
			</td>
		</tr>
	</tbody>
</table>