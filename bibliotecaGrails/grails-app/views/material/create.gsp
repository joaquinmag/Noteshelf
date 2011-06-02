<html>
  <head>
    <meta name="layout" content="main" />
    <title>Actualizar material</title>         
  </head>
  <body>
    <div class="body">
      <h1>Actualizar material. </h1>(Si el material ya existe no ser&aacute; agregado.)
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:form action="actualizar" method="post" enctype="multipart/form-data">
        <div class="dialog">
          <table>
	            <tr class="prop">
	            	<td class="name">
	                  <label for="material">Importar archivo excel: </label><input type="file" name="archivo"/>
	                </td>
	           	</tr>
          </table>
        </div>
        <div class="buttons">
          <span class="button">
            <input class="save" type="submit" value="Actualizar" onclick="this.value='Actualizando... Por favor espere...'; progress.style.display=''; this.disabled=true; this.form.submit();"/>
          </span>
        </div>
        <img id="progress" src="../images/progress.gif" style="display:none"/>
      </g:form>
      <g:form action="actualizarDesdeGDoc">
      	<div class="buttons">
          <span class="button">
          	<input class="save" type="submit" value="Actualizar desde GDoc" onclick="this.value='Actualizando... Por favor espere...'; progress.style.display=''; this.disabled=true; this.form.submit();"/>
          </span>
      	</div>
      </g:form>
    </div>
  </body>
</html>
