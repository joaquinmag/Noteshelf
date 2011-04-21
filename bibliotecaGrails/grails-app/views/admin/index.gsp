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
                    <li class="controller"><g:link controller="prestamo" action="create">Crear</g:link></li>
                    <li class="controller"><g:link controller="prestamo" action="list">Registrar devoluci&oacute;n</g:link></li>
                    <li class="controller"><g:link controller="prestamo" action="list">Ver y mandar recordatorios</g:link></li>
                </ul>
                <h2>Material:</h2>
                <ul>
                    <li class="controller"><g:link controller="material" action="create">Actualizar</g:link></li>
                    <li class="controller"><g:link controller="material" action="list">Editar</g:link></li>
                </ul>
            </div>
        </div>
    </body>
</html>
