<div id="header">
  <div id="header_inner" class="fixed">
    <div id="loginHeader"><g:loginControl /><g:render template="/layouts/buscaApuntes" /></div>

    <div id="logo">
      <g:if test="${session?.usuario?.admin}">
        <g:link controller="admin" action="index"><h1><span>Biblioteca de apuntes</span></h1></g:link>
      </g:if>
      <g:else>
        <g:link controller="apunte" action="index"><h1><span>Biblioteca de apuntes</span></h1></g:link>
      </g:else>
    </div>

    <div id="menu">
      <ul>
        <li><g:link controller="apunte" action="index" class="active">Apuntes</g:link></li>
        <li><g:link controller="resumen" action="index">Resumen</g:link></li>
        <li><g:link controller="prestamo" action="index">Prestamos</g:link></li>
        <li><g:link controller="usuario" action="index">Usuarios</g:link></li>
        <li><a href="mailto:bibliotecadeapuntesfiuba@gmail.com">Enviar mail</a></li>
      </ul>
    </div>

  </div>
</div>
