<g:render template="/layouts/buscaApuntes" />

<div id="header">
	<g:if test="${session?.usuario?.admin}">
		<g:link controller="admin" action="index" class="header-main"><p>Biblioteca</p><p>de apuntes</p></g:link>
	</g:if>
	<g:else>
		<a class="header-main" href="${resource(dir:'')}"><p>Biblioteca</p><p>de apuntes</p></a>
	</g:else>
	<div id="loginHeader"><g:loginControl /></div>
</div>