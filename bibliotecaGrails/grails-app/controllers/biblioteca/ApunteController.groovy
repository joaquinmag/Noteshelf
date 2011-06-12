package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class ApunteController {

	static scaffold = biblioteca.Apunte

}
