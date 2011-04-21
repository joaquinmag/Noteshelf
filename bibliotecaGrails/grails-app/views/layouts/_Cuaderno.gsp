<span class="item">Material: </span>
<span class="valor">Cuaderno</span>
<span class="item">Id: </span>
<span class="valor">${material.id}</span>
<table>
	<tbody>
		<tr class="prop">
			<td valign="top">
				<span class="item">Materia: </span>
				<span class="valor">${material.codigoMateria+" "+material.materia}</span>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<span class="item">Cuatrimestre: </span>
				<span class="valor">${material.cuatrimestre}</span>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<span class="item">Tipo: </span>
				<span class="valor">${material.tipo}</span>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top">
				<span class="item">C&aacute;tedra: </span>
				<span class="valor">${material.catedra}</span>
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
