package biblioteca
import org.apache.commons.lang.RandomStringUtils;
import biblioteca.usuario.Usuario
import biblioteca.usuario.Rol
import biblioteca.usuario.UsuarioRol
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class UsuarioController {
  
	def springSecurityService
	def emailConfirmationService
	def MailService
	
	static scaffold = biblioteca.usuario.Usuario
	
//	def login = {}
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
	def confirmado = {}
	
//	@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
//	def reiniciarPassword = {
//		if (params.login) {
//			Usuario usuario = Usuario.findByLogin(params.login)
//			if (usuario) {
//				def password =  RandomStringUtils.random(8, true, true)
//				usuario.password = password.encodeAsSHA()
//
//				if (!usuario.save(flush:true)) {
//					usuario.errors.each {
//						log.error "err $it"
//					}
//					flash.message = "Error al generar el nuevo password."
//					redirect(controller:"usuario", action:"login")
//				} else {
//					sendMail {
//						to usuario.email
//						subject "Nuevo password biblioteca de apuntes"
//						body(view:"emailPassModificado", model: [usuario:usuario.username,password:password])
//					}
//					flash.message = "El nuevo password fue enviado a tu email."
//					redirect(controller:"usuario", action:"login")
//				}
//			}
//			else {
//				flash.message = "Usuario no encontrado. Ingres&aacute; tu usuario nuevamente."
//				redirect(controller:"usuario", action:"login")
//			}
//		}
//		else {
//			flash.message = "Ingres&aacute; tu login para poder enviarte un nuevo password a tu email."
//			redirect(controller:"usuario", action:"login")
//		}
//	}
	
	@Secured(['ROLE_ADMIN'])
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
	
	//		SaveUsuario(controller:'usuario', action:"(save)") {
	//			before = {
	//				params.rol = "cliente"
	//				if (params.login == "admin")
	//					params.rol = "admin"
	//
	//				params.fechaPenalizacion =  Calendar.getInstance().getTime()
	//				def usuario = new Usuario()
	//				usuario.properties = params
	//
	//				usuario.validate()
	//				if (!usuario.hasErrors()) {
	//					emailConfirmationService.sendConfirmation(params.email,
	//							"Biblioteca de apuntes", [from:'bibliotecaapuntesfiuba@gmail.com',view:"/usuario/email",
	//								usuarioInstance:usuario])
	//					flash.message = "Por favor confirm&aacute; tu registro desde tu direcci&oacute;n de e-mail."
	//				}
	//				render(view:'list', controller:'material')
	//			}
	//		}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def create = {
		def usuarioInstance = new Usuario()
		usuarioInstance.properties = params
		return [usuarioInstance: usuarioInstance]
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def save = {
		def rol = Rol.findByAuthority('ROLE_USUARIO')
		 
		if (params.login == "admin")
			rol = Rol.findByAuthority('ROLE_ADMIN')

		params.fechaPenalizacion =  Calendar.getInstance().getTime()
		params.enabled = false
		params.accountExpired = false
		params.accountLocked = false
		params.passwordExpired = false

		def usuarioInstance = new Usuario(params)
		usuarioInstance.password = springSecurityService.encodePassword(params.password)
	
		if (usuarioInstance.save(flush: true)) {
			UsuarioRol.create usuarioInstance, rol, true
			flash.message = "Por favor confirm&aacute; tu registro desde tu direcci&oacute;n de e-mail."
			emailConfirmationService.sendConfirmation(params.email,
				"Biblioteca de apuntes", [from:'bibliotecaapuntesfiuba@gmail.com',view:"/usuario/email",
				usuario:usuarioInstance.username, password:params.password])
			redirect(controller:"material", action:"list")
		}
		else {
			render(view: "create", model: [usuarioInstance: usuarioInstance])
		}
	}
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
	def edit = {
		if(springSecurityService.isLoggedIn()) {
			// Si solo pone usuario/edit
			if (params.id == null) {
				redirect(controller:"material", action:"list")
			}

			def usuario = Usuario.get(params.id)
			if (usuario) {
				if (springSecurityService.principal.username == usuario.username) {
					flash.message = "Si modific&aacute;s tu direcci&oacute;n de e-mail tendr&aacute;s que confirmarla nuevamente."
					return [usuarioInstance: usuario]
				} else {
					redirect(controller:"material", action:"list")
					flash.message = "S&oacute;lo pod&eacute;s modificar tus propios datos."
				}
			}
		}
		else {
			flash.message = "Inici&aacute; sesi&oacute;n para modificar tus datos."
			redirect(controller:"usuario", action:"login")
		}
	}
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
	def update = {
		if(springSecurityService.isLoggedIn()) {
			def userInstance = Usuario.get(params.id)
			String emailAnterior = userInstance.email

			if (userInstance) {
				if (params.version) {
					def version = params.version.toLong()
				}
				
				println userInstance.password
				println params.password
				println springSecurityService.encodePassword(params.password)
				if (userInstance.password != params.password) {
					params.password = springSecurityService.encodePassword(params.password)
				}

				userInstance.properties = params
				
				if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
					if (springSecurityService.loggedIn &&
							springSecurityService.principal.username == userInstance.username) {
							springSecurityService.reauthenticate userInstance.username
					}
							
					String confirmarMail = ""
					if(emailAnterior != params['email']) {
						userInstance.enabled = false
						emailConfirmationService.sendConfirmation(params.email,
							"Biblioteca de apuntes", [from:'bibliotecaapuntesfiuba@gmail.com',view:"/usuario/emailUsuarioModificado"])
						confirmarMail = "Por favor confirm&aacute; tu registro desde tu nueva direcci&oacute;n de e-mail."
					}
					flash.message = "Datos actualizados correctamente! "+confirmarMail
					redirect(controller:"material", action:"list")
					return true
				}
				else {
					render(view: "edit", model: [userInstance: userInstance])
				}
			}
			else {
				flash.message = "Inici&aacute; sesi&oacute;n para modificar tus datos."
				redirect(controller:"login", action:"auth")
				return false
			}
		}
	}
}
