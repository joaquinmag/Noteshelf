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
	def mailService
	
	static scaffold = biblioteca.usuario.Usuario
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def confirmado = {}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def reiniciarPassword = {
		
		if (params.email) {
			Usuario usuario = Usuario.findByEmail(params.email)
			if (usuario) {
				def password =  RandomStringUtils.random(8, true, true)
				usuario.password = springSecurityService.encodePassword(password)

				if (!usuario.save(flush:true)) {
					flash.message = "Error al generar el nuevo password."
				} else {
					sendMail {
						to usuario.email
						subject "Nuevo password biblioteca de apuntes"
						body(view:"emailPassModificado", model: [usuario:usuario.username,password:password])
					}
					flash.message = "El nuevo password fue enviado a tu email."
				}
				redirect(controller:"login", action:"index")
			}
			else {
				flash.message = "No existe un usuario registrado con ese e-mail. Ingres&aacute; tu e-mail nuevamente."
				redirect(controller:"usuario", action:"olvidoClave")
			}
		}
		else {
			flash.message = "Ingres&aacute; tu direcci&oacute;n de e-mail para poder enviarte un nuevo password."
			redirect(controller:"usuario", action:"olvidoClave")
		}
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def olvidoClave = {}
	
	@Secured(['ROLE_ADMIN'])
	def searchAJAX = {
		def usuarios = Usuario.findAllByUsernameLike("%${params.query}%")

		//Create XML response
		render(contentType: "text/xml") {
			results() {
				usuarios.each { usuario ->
					result(){
						name(usuario.username)
								//Optional id which will be available in onItemSelect
								id(usuario.id)
					}
				}
			}
		}
	}
	
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
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
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
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def update = {
		if(springSecurityService.isLoggedIn()) {
			def usuarioInstance = Usuario.get(params.id)
			String emailAnterior = usuarioInstance.email

			if (usuarioInstance) {
				if (params.version) {
					def version = params.version.toLong()
				}

				if (usuarioInstance.password != params.password) {
					params.password = springSecurityService.encodePassword(params.password)
				}

				usuarioInstance.properties = params
				usuarioInstance.validate()
				if (!usuarioInstance.hasErrors() && usuarioInstance.save(flush: true)) {
					if (springSecurityService.loggedIn &&
							springSecurityService.principal.username == usuarioInstance.username) {
							springSecurityService.reauthenticate usuarioInstance.username
					}
							
					String confirmarMail = ""
					if(emailAnterior != params['email']) {
						usuarioInstance.enabled = false
						emailConfirmationService.sendConfirmation(params.email,
							"Biblioteca de apuntes", [from:'bibliotecaapuntesfiuba@gmail.com',view:"/usuario/emailUsuarioModificado"])
						confirmarMail = "Por favor confirm&aacute; tu registro desde tu nueva direcci&oacute;n de e-mail."
					}
					flash.message = "Datos actualizados correctamente! "+confirmarMail
					redirect(controller:"material", action:"list")
					return true
				}
				else {
					render(view: "edit", model: [usuarioInstance: usuarioInstance])
				}
			}
			else {
				flash.message = "Inici&aacute; sesi&oacute;n para modificar tus datos."
				redirect(controller:"login", action:"auth")
				return false
			}
		}
	}
	
	@Secured(['ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def delete = {
		def usuarioInstance = Usuario.get(params.id)

        if (usuarioInstance) {
            try {
				UsuarioRol.removeAll(usuarioInstance)
				usuarioInstance.delete(flush: true)

                flash.message = "Usuario eliminado correctamente."
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Error al eliminar el usuario."
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "No existe un usuario con ese id."
            redirect(action: "list")
        }
	}
}
