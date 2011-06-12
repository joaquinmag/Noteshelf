package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class ResumenController {

	static scaffold = biblioteca.Resumen

}
