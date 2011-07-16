<html>
    <head>
        <title>Biblioteca de apuntes - administración</title>
        <meta name="layout" content="main" />
        <style type="text/css" media="screen">

        h2 {
            margin-top:15px;
            margin-bottom:15px;
            font-size:1.2em;
        }
        #pageBody {
            margin-left:20px;
            margin-right:20px;
        }
        </style>
    </head>
    <body>
         <div id="pageBody">
            <h1>Biblioteca de apuntes - Consola de administraci&oacute;n</h1>

            <div id="controllerList" class="dialog">
                <h2>Pr&eacute;stamos:</h2>
                <ul>
                    <li class="controller"><g:link controller="prestamo" action="create">Crear pr&eacute;stamo</g:link></li>
                    <li class="controller"><g:link controller="prestamo" action="list">Registrar devoluci&oacute;n de pr&eacute;stamo</g:link></li>
                    <li class="controller"><g:link controller="prestamo" action="list">Ver y mandar recordatorios de pr&eacute;stamos</g:link></li>
                </ul>
                <h2>Material:</h2>
                <ul>
                    <li class="controller"><g:link controller="material" action="create">Actualizar material</g:link></li>
                    <li class="controller"><g:link controller="material" action="list">Editar material</g:link></li>
                </ul>
                <h2>Feriados:</h2>
                <ul>
                    <li class="controller"><g:link controller="feriado" action="index">Administrar feriados</g:link></li>
                </ul>
                <h2>Usuarios:</h2>
                <ul>
                    <li class="controller"><g:link controller="usuario" action="index">Administrar usuarios</g:link></li>
                </ul>
            </div>
        </div>
    </body>
</html>
