package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class CuadernoController {

	static scaffold = biblioteca.Cuaderno
	
}
