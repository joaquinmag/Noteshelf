<g:render template="/layouts/buscaApuntes" />
<div id="header">
<div id="header_inner" class="fixed">
        <div id="loginHeader"><g:loginControl /></div>
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
            <li><g:link controller="apunte" action="index">Apuntes</g:link></li>
            <li><g:link controller="resumen" action="index">Res&uacute;menes</g:link></li>
            <li><g:link controller="cuaderno" action="index">Cuadernos</g:link></li>
            <li><g:link controller="prestamo" action="index">Pr&eacute;stamos</g:link></li>
            <li><g:link controller="usuario" action="index">Usuarios</g:link></li>
            <li><a href="mailto:bibliotecadeapuntesfiuba@gmail.com">Enviar mail</a></li>
          </ul>
        </div>
</div>
</div>
