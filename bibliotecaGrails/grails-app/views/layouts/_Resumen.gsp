<span class="item">Material: </span>
<span class="valor">${material.descripcion}</span>
<span class="item">Id: </span>
<span class="valor">${material.id}</span>
<table>
	<tbody>
		<tr class="prop">
			<td valign="top">
				<span class="item">Tipo: </span>
				<span class="valor">Resumen</span>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<span class="item">Materia: </span>
				<span class="valor">${material.codigoMateria+" "+material.materia}</span>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<span class="item">Fecha de alta: </span>
				<span class="valor">${material.dateCreated.format("dd, MMMM, yyyy")}</span>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<span class="item">Autor: </span>
				<span class="valor">${material.autor}</span>
			</td>
		</tr>
	</tbody>
</table>