package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class AdminController{
	
//	def beforeInterceptor = [action:this.&auth]
//	
//	def auth() {
//		if(!session.usuario) {
//			redirect(controller:"usuario", action:"login")
//			return false
//		}
//		
//		if(!session.usuario.admin){
//			flash.message = "Solo para administradores."
//			redirect(controller:"material", action:"list")
//			return false
//		}
//	}

	def index = {}

}