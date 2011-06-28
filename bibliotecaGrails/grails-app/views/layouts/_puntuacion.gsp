<div id="puntuacion">
	<g:puedePuntuar material="${material}">
		<richui:rating dynamic="true" units="5" rating="${material.getPuntuacion()}" id="${material.id}" controller="material" action="puntuar" />
	</g:puedePuntuar>
	<g:noPuedePuntuar material="${material}">
		<richui:rating dynamic="false" units="5" rating="${material.getPuntuacion()}" id="${material.id}" controller="material" action="puntuar" />
	</g:noPuedePuntuar>
	<g:mostrarPuntuacion material="${material}">Tu puntuaci&oacute;n: </g:mostrarPuntuacion>
</div>