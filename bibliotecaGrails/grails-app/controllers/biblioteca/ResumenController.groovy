package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class ResumenController {

	static scaffold = biblioteca.Resumen

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def list ={
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[resumenInstanceList: Resumen.list(params), resumenInstanceTotal: Resumen.count()]
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def index = {
		redirect(action:'list')
	}
}
