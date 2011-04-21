<%@ page contentType="text/html"%>
<html>
<head>
<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
</head>
	<h1><span style="color: #90d12e; font-family: 'verdana', 'geneva';">Biblioteca de apuntes</span></h1>
	<h2><span style="font-family: 'verdana', 'geneva'; color: #339966;">Pr&eacute;stamo realizado:</span></h2>
	<h3><span style="font-family: 'verdana', 'geneva';"><g:render template="/layouts/${prestamoInstance.materialPrestado.getClass().getSimpleName()}" model="[material: prestamoInstance.materialPrestado]"/></span></h3>
	<h3><span style="font-family: 'verdana', 'geneva';">Fecha de pr&eacute;stamo: <g:formatDate format="yyyy-MM-dd hh:mm:ss" date=${Calendar.getInstance().getTime()}/></span></h3>
	<h3><span style="font-family: 'verdana', 'geneva';">Fecha de devoluci&oacute;n: ${prestamoInstance.devolucion}</span></h3>
	<h2><span style="font-family: 'verdana', 'geneva'; color: #e83817;">Record&aacute; que no podr&aacute;s pedir pr&eacute;stamos por tantas semanas como d&iacute;as te retrases en la devoluci&oacute;n.</span></h2>
	<p>&nbsp;</p>
</html>