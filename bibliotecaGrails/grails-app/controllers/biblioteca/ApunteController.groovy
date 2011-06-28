package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class ApunteController {

	static scaffold = biblioteca.Apunte
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def list ={
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[apunteInstanceList: Apunte.list(params), apunteInstanceTotal: Apunte.count()]
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def index = {
		redirect(action:'list')
	}
}
