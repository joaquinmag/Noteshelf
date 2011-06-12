package biblioteca

import java.util.Calendar;
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class PrestamoController {

	static scaffold = biblioteca.Prestamo
	
	def MailService
	
	def index = {
		redirect (controller:"prestamo", action:"list")
	}
	
	def edit = {
		def prestamo = Prestamo.get(params.id)
		
		if (prestamo.pendiente)
			redirect (controller:"prestamo", action:"devolver", params:[id:prestamo.id,material:prestamo.materialPrestado,usuario:prestamo.usuario,devolucion:prestamo.devolucion])
		else {
			flash.message = "Este pr&eacute;stamo ya fue devuelto."
			redirect (controller:"prestamo", action:"list")
		}
	}
	
	def devolver = {}
	
	@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
	def list = {
		if (session?.usuario) {
			if (session?.usuario?.rol == "cliente"){
				def prestamosPropios = Prestamo.findAllByUsuario(session.usuario,[sort:"dateCreated",order:"desc"])
				[prestamoInstanceList: prestamosPropios,prestamoInstanceTotal: prestamosPropios.count()]
			}
			else {
				[prestamoInstanceList: Prestamo.list(sort:"pendiente",order:"desc"),prestamoInstanceTotal: Prestamo.count()]
			}
		} else
			redirect (controller:"material", action:"list")
	}
	
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
}
