package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class ComentarioController {

	static scaffold = biblioteca.Comentario
	
}
