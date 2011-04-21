<g:if test="${session.usuario?.puedePuntuar(material)}">
	<div id="puntuacion">
		<richui:rating dynamic="true" units="5" rating="${material.getPuntuacion()}" id="${material.id}" controller="material" action="puntuar" />
	</div>
</g:if>
<g:else>
	<div id="puntuacion">
		<richui:rating dynamic="false" units="5" rating="${material.getPuntuacion()}" id="${material.id}" controller="material" action="puntuar" />
	</div>
</g:else>