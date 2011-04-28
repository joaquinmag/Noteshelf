<div id="search">
  <g:form url='[controller: "material", action: "buscar"]' id="materialSearchForm" name="materialSearchForm" method="get">
    <g:textField class="text" name="q" value="${params.q}" />
    <input type="submit" class="button" value="Buscar material" />
  </g:form>
</div>

