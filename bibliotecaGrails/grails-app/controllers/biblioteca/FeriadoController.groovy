package biblioteca

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN','IS_AUTHENTICATED_FULLY'])
class FeriadoController {

	static scaffold = biblioteca.Feriado

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = "mes"
		[feriadoInstanceList: Feriado.list(params), feriadoInstanceTotal: Feriado.count()]
	}
	
	def save = {
		def feriadoInstance = new Feriado(params)
		if (feriadoInstance.validarFecha() && feriadoInstance.save(flush: true)) {
			flash.message = "Feriado guardado correctamente"
			redirect(action: "show", id: feriadoInstance.id)
		}
		else {
			flash.message = "Error al guardar el feriado. Revis&aacute; los datos por favor."
			render(view: "create", model: [feriadoInstance: feriadoInstance])
		}		
	}
	
	def update = {
		def feriadoInstance = Feriado.get(params.id)
		if (feriadoInstance) {
			if (params.version) {
				def version = params.version.toLong()
			}
			
			feriadoInstance.properties = params
			if (!feriadoInstance.hasErrors() && feriadoInstance.validarFecha() && feriadoInstance.save(flush: true)) {
				flash.message = "Feriado actualizado correctamente"
				redirect(action: "show", id: feriadoInstance.id)
			}
			else {
				flash.message = "Error al actualizar el feriado. Revis&aacute; los datos por favor."
				render(view: "edit", model: [feriadoInstance: feriadoInstance])
			}
		}
		else {
			flash.message = "Error al actualizar el feriado"
			redirect(action: "list")
		}
	}
}
