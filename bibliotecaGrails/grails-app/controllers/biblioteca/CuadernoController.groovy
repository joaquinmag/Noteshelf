package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class CuadernoController {

	static scaffold = biblioteca.Cuaderno

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def list ={
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[cuadernoInstanceList: Cuaderno.list(params), cuadernoInstanceTotal: Cuaderno.count()]
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def index = {
		redirect(action:'list')
	}
}
