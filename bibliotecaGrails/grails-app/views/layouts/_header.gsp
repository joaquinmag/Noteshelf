<div id="header">
  <div id="header_inner" class="fixed">
    <g:loginControl /><g:render template="/layouts/buscadorMaterial" />

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
