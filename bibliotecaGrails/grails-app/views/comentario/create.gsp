<html>
  <head>
    <meta name="layout" content="main" />
    <title>Comentar</title>
    <script>

      function textCounter(textarea, countdown, maxlimit)
      {
        textareaid = document.getElementById(textarea);
        if (textareaid.value.length > maxlimit)
          textareaid.value = textareaid.value.substring(0, maxlimit);
        else
          document.getElementById(countdown).value = '('+(maxlimit-textareaid.value.length)+' caracteres restantes)';
      }

    </script>

    <style>
      #ta1count { border: 0; }
    </style>
  </head>
  <body>
    <div class="body">
      <h1>Comentar</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:form action="save" >
        <div class="dialog">
          <table>
            <tbody>
	            <tr class="prop">
	            	<td class="name">
	                  <label for="material">Material: <g:materialPorId id="${params['material']}" /></label>
	                </td>
	                <g:hiddenField name="material" value="${params['material']}"/>
	           	</tr> 
	            <tr class="prop">
	                <td class="name">
	                  <label for="comentario">Comentario:</label>
	                </td>
	                <td>
					  <textarea id="comentario" name="comentario" rows=3 cols=20 onKeyDown="textCounter('comentario','ta1count',255);" onKeyUp="textCounter('comentario','ta1count',255);"></textarea>
					  <input id="ta1count" readonly type="text" size="30"/>
	                </td>
	            </tr>
				<g:hiddenField name="autor" value="${session.usuario}"/>
			</tbody>
          </table>
        </div>
        <div class="buttons">
          <span class="button">
            <input class="save" type="submit" value="Comentar" />
          </span>
        </div>
      </g:form>
    </div>
    <script type="text/javascript">textCounter('comentario','ta1count',255);</script>
  </body>
</html>
