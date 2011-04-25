<div id="header">
<div id="header_inner" class="fixed">
        <div id="loginHeader"><g:loginControl /><g:render template="/layouts/buscaApuntes" /></div>
        
        <div id="logo">
          	<g:if test="${session?.usuario?.admin}">
                  <g:link controller="admin" action="index"><h1><span>Biblioteca de apuntes</span></h1></g:link>
        	</g:if>
                <g:else>
                  <g:link controller="apunte" action="index"><h1><span>Biblioteca de apuntes</span></h1></g:link>
                </g:else>
        </div>

        <div id="menu">
                <ul>
                        <li><a href="#" class="active">Blog</a></li>
                        <li><a href="#">About Me</a></li>
                        <li><a href="#">Photos</a></li>
                        <li><a href="#">Resources</a></li>
                        <li><a href="#">Contact Me</a></li>
                </ul>
        </div>

</div>
</div>