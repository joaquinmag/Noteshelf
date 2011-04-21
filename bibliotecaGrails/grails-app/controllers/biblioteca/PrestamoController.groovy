package biblioteca

import java.util.Calendar;


class PrestamoController {

	def MailService
	
	final long MILSEGS_POR_DIA = 24 * 60 * 60 * 1000
	
	def scaffold = true
	
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
			
			if (prestamo.devolucion.before(prestamo.devolucionReal)) {
				def usuario = Usuario.get(prestamo.usuario.id)
				usuario.fechaPenalizacion = prestamo.devolucionReal
				usuario.semanasPenalizacion = (prestamo.devolucionReal.getTime()-prestamo.devolucion.getTime())/ MILSEGS_POR_DIA
				penalizacion = "Penalizaci&oacute;n de "+usuario.semanasPenalizacion+" semanas por demorar en la devoluci&oacute;n."
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
