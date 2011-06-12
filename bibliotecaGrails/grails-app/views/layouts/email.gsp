<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
		<div id="header">
			<a class="header-main" href="${resource(dir:'')}"><p>Biblioteca</p><p>de apuntes</p></a>
			<div id="loginHeader"></div>
		</div>
		<g:layoutBody />
    </body>
    ${usuarioInstance.username}
</html>