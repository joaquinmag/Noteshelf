package biblioteca
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class ComentarioController {
	
	def springSecurityService
	
	static scaffold = biblioteca.Comentario
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def update = {
		if(springSecurityService.loggedIn){
			def comentarioInstance = Comentario.get(params.id)
			comentarioInstance.comentario = params.comentario
			if('ROLE_ADMIN' in springSecurityService.principal.authorities*.toString()){
				if (!comentarioInstance.hasErrors() && comentarioInstance.save(flush:true)) {
					flash.message = "Comentario actualizado."
					redirect(controller:"material", action:"list")
				}
				else {
					render(view: "edit", model: [comentarioInstance: comentarioInstance])
				}
			} else if(!(comentarioInstance in Comentario.findAllByAutor(springSecurityService.currentUser))) {
				flash.message = "Este comentario no es tuyo. No pod&eacute;s modificarlo."
				redirect(controller:"material", action:"list")
			} else {
				if (!comentarioInstance.hasErrors() && comentarioInstance.save(flush:true)) {
					flash.message = "Comentario actualizado."
					redirect(controller:"material", action:"list")
				}
				else {
					render(view: "edit", model: [comentarioInstance: comentarioInstance])
				}
			}
		} else {
			flash.message = "Deb&eacutes; iniciar sesi&oacute;n antes de modificar un comentario."
			redirect(controller:"login", action:"auth")
			return false
		}
	}
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def save = {
		if (springSecurityService.loggedIn){
			def material = Material.get(params.material)
			if('ROLE_ADMIN' in springSecurityService.principal.authorities*.toString()){
				def comentarioInstance = new Comentario(autor:Usuario.findByUsername("admin"), material:material, comentario:params.comentario)
				if (comentarioInstance.save(flush:true)) {
					flash.message = "Comentario guardado."
					redirect(controller:"material", action:"list")
				}
				else {
					render(view: "create", model: [comentarioInstance: comentarioInstance])
				}
			} else if(session.usuario.puedeComentar(material)) {
				def comentarioInstance = new Comentario(autor:Usuario.findByUsername(params.autor), material:Material.get(params.material), comentario:params.comentario)
				if (comentarioInstance.save(flush:true)) {
					flash.message = "Comentario guardado."
					redirect(controller:"material", action:"list")
				}
				else {
					render(view: "create", model: [comentarioInstance: comentarioInstance])
				}
			} else {
				flash.message = "Nunca retiraste este material. No pod&eacute;s comentarlo."
				redirect(controller:"material", action:"list")
			}
		} else {
			flash.message = "Deb&eacutes; iniciar sesi&oacute;n antes de comentar."
			redirect(controller:"login", action:"auth")
			return false
		}
	}
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def create = {
		def comentarioInstance = new Comentario()
       comentarioInstance.properties = params
        return [comentarioInstance: comentarioInstance]
	}
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def edit = {
		def comentarioInstance = Comentario.get(params.id)
		if (!comentarioInstance) {
			flash.message = "El comentario que quer&eacute;s editar no existe."
			redirect(controller:"material", action:"list")
		}
		else {
			return [comentarioInstance: comentarioInstance]
		}
	}

	@Secured(['ROLE_USUARIO','ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def delete = {
		if (springSecurityService.loggedIn){
			//Si el comentario es suyo o es admin borra el comentario
			if(springSecurityService.principal.username == Comentario.get(params.id).autor.username || 
				'ROLE_ADMIN' in springSecurityService.principal.authorities*.toString()) {
				
				def comentarioInstance = Comentario.get(params.id)
				if (comentarioInstance) {
					try {
						comentarioInstance.delete(flush: true)
						flash.message = "Comentario borrado."
						redirect(controller:"material", action:"list")
					}
					catch (org.springframework.dao.DataIntegrityViolationException e) {
						flash.message = "Error al borrar el comentario."
						redirect(controller:"material", action:"list")
					}
				}
				else {
					flash.message = "El comentario que quer&eacute;s borrar no existe."
					redirect(controller:"material", action:"list")
				}
				
			} else {
				flash.message = "Este comentario no es tuyo."
				redirect(controller:"material", action:"list")
			}
		} else {
			flash.message = "Por favor inici&aacute; sesi&oacute;n antes de continuar."
			redirect(controller:"login", action:"auth")
		}
	}
}
