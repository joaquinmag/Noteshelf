<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Biblioteca de apuntes" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'style_red.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <g:render template="/layouts/header" />
        <div id="main">
          <g:layoutBody />
        </div>
    </body>
</html>