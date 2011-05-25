<div id="search">
	<g:form url='[controller: "material", action: "buscar"]' id="materialSearchForm" name="materialSearchForm" method="get">
		<g:textField name="q" value="${params.q}" />
		<input type="submit" value="Buscar material" />
	</g:form>
</div>