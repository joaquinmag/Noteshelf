<html>
  <head>
    <meta name="layout" content="main" />
    <resource:autoComplete skin="default" />
    <title>Crear pr&eacute;stamo</title>         
  </head>
  <body>
    <div class="body">
      <h1>Crear pr&eacute;stamo</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${prestamoInstance}">
   		<div class="errors">
          <g:renderErrors bean="${prestamoInstance}" as="list" />
		</div>
	  </g:hasErrors>
      <g:form action="save" >
        <div class="dialog">
          <table>
            <tbody>
	            <tr class="prop">
	            	<td class="name">
	                  <label for="devolucion">Fecha de devoluci&oacute;n: </label>
	                </td>
	                <td>
	                	<g:formatDate format="dd/MM/yyyy hh:mm:ss" date="${prestamoInstance.devolucion}" />
	                </td>
	           	</tr> 
	            <tr class="prop">
	                <td class="name">
	                  <label for="materialPrestado">Material prestado:</label>
	                </td>
	                <td>
	                  <richui:autoComplete forceSelection="true" name="materialPrestado" action="${createLinkTo('dir': 'material/searchAJAX')}" />
	                </td>
	            </tr>
	            <tr class="prop">
	                <td class="name">
	                  <label for="usuario">Usuario:</label>
	                </td>
	                <td>
	                  <richui:autoComplete forceSelection="true" name="usuario" action="${createLinkTo('dir': 'usuario/searchAJAX')}" />
	                </td>
	            </tr>
			</tbody>
          </table>
        </div>
        <div class="buttons">
          <span class="button">
            <input class="save" type="submit" value="Crear pr&eacute;stamo" />
          </span>
        </div>
      </g:form>
    </div>
  </body>
</html>
