package biblioteca
import org.apache.commons.lang.RandomStringUtils;

class UsuarioController {
  
	def scaffold = true
	
	def login = {}
	
	def confirmado = {}
		
	def autenticar = {
		def usuario = Usuario.findByLoginAndPassword(params.login,params.password.encodeAsSHA())
		if(usuario){
			if (!usuario.confirmado){
				flash.message = "Deb&eacute;s confirmar tu direcci&oacute;n de email primero."
				redirect(controller:"material", action:"list")
			} else {
				session.usuario = usuario
				flash.message = "Hola ${usuario.toString()}!"
				if(usuario.admin){
					redirect(controller:"admin", action:"index")
				} else
					redirect(controller:"material", action:"list")
			}
		}else{
		  flash.message = "Error, ${params.login}. Por favor vuelva a intentar."
		  redirect(action:"login")
		}
	 }
  
	def logout = {
		flash.message = "Chau ${session.usuario.login}"
		session.usuario = null
		redirect(controller:"material", action:"list")      
	}
	
	def reiniciarPassword = {
		if (params.login) {
			Usuario usuario = Usuario.findByLogin(params.login)
			if (usuario) {
				def password =  RandomStringUtils.random(8, true, true)
				usuario.password = password.encodeAsSHA()

				if (!usuario.save(flush:true)) {
					usuario.errors.each {
						log.error "err $it"
					}
					flash.message = "Error al generar el nuevo password."
					redirect(controller:"usuario", action:"login")
				} else {
					sendMail {
						to usuario.email
						subject "Nuevo password biblioteca de apuntes"
						body(view:"emailPassModificado", model: [login:usuario.login,password:password])
					}
					flash.message = "El nuevo password fue enviado a tu email."
					redirect(controller:"usuario", action:"login")
				}
			}
			else {
				flash.message = "Usuario no encontrado. Ingres&aacute; tu usuario nuevamente."
				redirect(controller:"usuario", action:"login")
			}
		}
		else {
			flash.message = "Ingres&aacute; tu login para poder enviarte un nuevo password a tu email."
			redirect(controller:"usuario", action:"login")
		}
	}
	
	def searchAJAX = {
		def usuarios = Usuario.findAllByLoginLike("%${params.query}%")

		//Create XML response
		render(contentType: "text/xml") {
			results() {
				usuarios.each { usuario ->
					result(){
						name(usuario.login)
								//Optional id which will be available in onItemSelect
								id(usuario.id)
					}
				}
			}
		}
	}
}
