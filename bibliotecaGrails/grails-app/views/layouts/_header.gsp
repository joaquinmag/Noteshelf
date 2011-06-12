<div id="header">
  <div id="header_inner" class="fixed">
    <sec:ifLoggedIn>
		Hola <sec:username/>!
		<sec:ifAllGranted roles="ROLE_ADMIN">
			<g:link controller="admin" action="index">[Administrar]</g:link>
		</sec:ifAllGranted>
		<sec:ifAllGranted roles="ROLE_USUARIO">
			<g:link controller="prestamo" action="list">[Mis pr&eacute;stamos]</g:link>
		</sec:ifAllGranted>
		<g:link controller="usuario" action="edit" id="${sec.loggedInUserInfo(field: 'id')}">[Mis datos]</g:link>
		<g:link controller="logout" action="index">[Logout]</g:link>
	</sec:ifLoggedIn>
	<sec:ifNotLoggedIn>
		<g:link controller='login' action='auth'>[Login]</g:link>
		<g:link controller='usuario' action='create'>[Registrarse]</g:link>
	</sec:ifNotLoggedIn>
	
    <g:render template="/layouts/buscadorMaterial" />

    <div id="logo">
      <g:if test="${session?.usuario?.admin}">
        <g:link controller="admin" action="index"><h1><span>Biblioteca de apuntes</span></h1></g:link>
      </g:if>
      <g:else>
        <g:link controller="material" action="index"><h1><span>Biblioteca de apuntes</span></h1></g:link>
      </g:else>
    </div>

    <div id="menu">
      <ul>
        <li id="tabapunte"><g:link controller="apunte" action="index">Apuntes</g:link></li>
        <li id="tabresumen"><g:link controller="resumen" action="index">Res&uacute;menes</g:link></li>
        <li id="tabcuaderno"><g:link controller="cuaderno" action="index">Cuadernos</g:link></li>
        <li id="tabprestamo"><g:link controller="prestamo" action="index">Pr&eacute;stamos</g:link></li>
        <li><a href="mailto:bibliotecadeapuntesfiuba@gmail.com">Enviar mail</a></li>
      </ul>
    </div>

  </div>
</div>
