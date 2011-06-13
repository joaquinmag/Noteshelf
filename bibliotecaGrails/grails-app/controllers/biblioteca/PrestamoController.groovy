package biblioteca

import java.util.Calendar;
import grails.plugins.springsecurity.Secured
import biblioteca.usuario.Usuario;

@Secured(['ROLE_ADMIN'])
class PrestamoController {

	static scaffold = biblioteca.Prestamo
	
	def MailService
	def springSecurityService
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
	def index = {
		redirect (controller:"prestamo", action:"list")
	}
	
	@Secured(['ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def devolver = {}

	@Secured(['ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def devolucion = {
		def prestamo = Prestamo.get(params.id)
		
		if (prestamo.pendiente) {
			prestamo.pendiente = false
			prestamo.devolucionReal = Calendar.getInstance().getTime()
			def penalizacion = ""
			def usuario = prestamo.usuario
			
			if (prestamo.debePenalizar()) {
				usuario.penalizar(prestamo)
				penalizacion = "Penalizaci&oacute;n de "+usuario.penalizacion.semanasPenalizacion+" semanas por demorar en la devoluci&oacute;n."
			}
				
			if (prestamo.save(flush:true)) {
				flash.message = "Prestamo correctamente cancelado. "+penalizacion
			}
		} else {
			flash.message = "Este pr&eacute;stamo ya fue devuelto."
		}
		redirect (controller:"prestamo", action:"list")
	}
	
	@Secured(['ROLE_ADMIN'])
	def recordar = {
		def prestamo = Prestamo.get(params.id)
		
		if (prestamo.pendiente) {
			mailService.sendMail {
				to prestamo.usuario.email
				subject "Recordatorio de prestamo"
				body (controller:"prestamo", view:"email", model: [prestamoInstance : prestamo])
			}
		} else {
			flash.message = "Este pr&eacute;stamo ya fue devuelto"
		}
		redirect (controller:"prestamo", action:"list")
	}
	
	@Secured(['ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def edit = {
		def prestamo = Prestamo.get(params.id)
		
		if (prestamo.pendiente)
			redirect (controller:"prestamo", action:"devolver", params:[id:prestamo.id,material:prestamo.materialPrestado,usuario:prestamo.usuario,devolucion:prestamo.devolucion])
		else {
			flash.message = "Este pr&eacute;stamo ya fue devuelto."
			redirect (controller:"prestamo", action:"list")
		}
	}
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
	def show = {
		if(springSecurityService.loggedIn){
			def prestamoInstance = Prestamo.get(params.id)
			if (!prestamoInstance) {
				flash.message = "Ese pr&eacute;stamo no existe."
				redirect(controller:"material", action: "list")
			}
			else {
				def mostrar = false
				if ('ROLE_ADMIN' in springSecurityService.principal.authorities*.toString()) {
					mostrar = true
				} else {
					def prestamosPropios = Prestamo.findAllByUsuario(springSecurityService.currentUser)
					if (!(prestamoInstance in prestamosPropios)) {
						flash.message = "Este pr&eacute;stamo no es tuyo."
						redirect(controller:"material", action:"list")
					} else
						mostrar = true
				}
				
				if (mostrar)
					[prestamoInstance: prestamoInstance]
			} 
		}
	}

	@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
	def list = {
		if (springSecurityService.loggedIn) {
			if ('ROLE_USUARIO' in springSecurityService.principal.authorities*.toString()) {
				def prestamosPropios = Prestamo.findAllByUsuario(springSecurityService.currentUser,[sort:"dateCreated",order:"desc"])
				[prestamoInstanceList: prestamosPropios,prestamoInstanceTotal: prestamosPropios.count()]
			}
			else {
				[prestamoInstanceList: Prestamo.list(sort:"pendiente",order:"desc"),prestamoInstanceTotal: Prestamo.count()]
			}
		} else
			redirect (controller:"material", action:"list")
	}
	
	@Secured([])
	def delete = {}
	
	@Secured(['ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def create = {
		def prestamoInstance = new Prestamo()
		prestamoInstance.properties = params
		return [prestamoInstance: prestamoInstance]
	}
	
	@Secured(['ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
	def save = {
		def usuario = Usuario.findByUsername(params.usuario)
		params.usuario = usuario
		if (params.materialPrestado.length() > 0) {
			params.materialPrestado = Material.get(params.materialPrestado.substring(params.materialPrestado.indexOf(" id:")+4))
		} else {
			params.materialPrestado = null
		}
		
		def prestamo = new Prestamo(params)
		prestamo.validate()
		if (prestamo.hasErrors()) {
			render(view: "create", controller: "prestamo", model: [prestamoInstance: prestamo])
		} else {
			//Prestamos pendientes
			if (usuario.tienePrestamosPendientes()) {
				flash.message = "El usuario tiene pr&eacute;stamos pendientes."
				redirect(controller:"prestamo", action:"list")
			} //Penalizacion vigente
				else if (usuario.penalizado) {
				flash.message = "El usuario tiene una penalizaci&oacute;n vigente."
				redirect(controller:"prestamo", action:"list")
			} //email no confirmado
				else if (!usuario.enabled) {
				flash.message = "El usuario no confirm&oacute; su email."
				redirect(controller:"prestamo", action:"list")
			} else {
				prestamo.pendiente = true
				if (prestamo.save(flush: true)){
					mailService.sendMail {
						to prestamo.usuario.email
						subject "Nuevo prestamo"
						body (controller:"prestamo", view:"email", model: [prestamoInstance : prestamo])
					}
					flash.message = "El pr&eacute;stamo fue creado correctamente."
				}
			}
		}
		redirect(controller:"prestamo", action:"list")
	}
}
