package biblioteca
import grails.converters.*

class MaterialController {

	def scaffold = true
	def ParsearExcelService

	def buscar = {
		flash.message = "Resultados de la b&uacute;squeda para: ${params.q}"
		
		params.q = params.q.encodeAsSearch()
			
		def resultsMap = Material.search(params.q, params)

		render(view:'list',
			model:[materialInstanceList:resultsMap.results,materialInstanceTotal:Material.countHits(params.q)])
	}
	
	def searchAJAX = {
		def materiales = Apunte.findAllByNombreLikeOrTemaLike("%${params.query}%","%${params.query}%")
		materiales.addAll(Cuaderno.findAllByMateriaLikeOrCatedraLike("%${params.query}%","%${params.query}%"))
		materiales.addAll(Resumen.findAllByAutorLikeOrMateriaLike("%${params.query}%","%${params.query}%"))
		
		//Create XML response
		render(contentType: "text/xml") {
			results() {
				materiales.each { material ->
					result(){
						name(material.toString()+" id:"+material.id)
								//Optional id which will be available in onItemSelect
								id(material.id)
					}
				}
			}
		}
	}
	
	def actualizar = {
		def f = request.getFile('archivo')
	    if(!f.empty) {

			f.transferTo( new File('material.xls') )
			try {
				MaterialExcelImporter importer = new MaterialExcelImporter("material.xls");
				def cuadernosMapList = importer.getCuadernos();
				def apuntesMapList = importer.getApuntes();
				def resumenesMapList = importer.getResumenes();
				
				def total = cuadernosMapList.size()+apuntesMapList.size()+resumenesMapList.size()
				def cuadernosAgregados = 0
				def apuntesAgregados = 0
				def resumenesAgregados = 0
				
				cuadernosMapList.each { Map cuadernoParams ->
					def cuaderno = new Cuaderno(cuadernoParams)
					
					if (!Cuaderno.findAll(cuaderno)) {
						if (cuaderno.save())
							cuadernosAgregados++
					}
				}
				
				apuntesMapList.each { Map apunteParams ->
					def apunte = new Apunte(apunteParams)
					if (!Apunte.findAll(apunte)) {
						if (apunte.save())
							apuntesAgregados++
					}
				}
				
				resumenesMapList.each { Map resumenParams ->
					def resumen = new Resumen(resumenParams)
					if (!Resumen.findAll(resumen)) {
						if (resumen.save())
							resumenesAgregados++
					}
				}
					
					flash.message = cuadernosAgregados+" cuadernos, "+apuntesAgregados+" apuntes y "+resumenesAgregados+" resumenes agregados de un total de "+total+" importados."
					
			} catch (Exception e) {
				flash.message = 'Error al leer el archivo. Tipo o formato inesperado'
			}

			redirect(action:'list')
	    }    
	    else {
	       flash.message = 'El archivo no puede estar vac&iacute;o'
	       redirect(action:'create')
	    }
	}
	
	def actualizarDesdeGDoc = {
		def authToken = ParsearExcelService.createAuthToken()
		println authToken
		ParsearExcelService.obtenerArchivo(authToken).entry.each{
			//def cuad = new Cuaderno(codigoMateria:it."gsx:title",materia:it."gsx:_cokwr",catedra:it."gsx:_cre1l",cuatrimestre:it."gsx:_ciyn3",tipo:it."gsx:_cztg3",autor:it."gsx:_ckd7g")
			println it//."title"+" "+it."_cokwr"+" "+it._cre1l+" "+it._ciyn3+" "+it._cztg3+" "+it._ckd7g
		}
	}
	
	def puntuar = {
		
		def material = Material.get(params.id)
		
		if(request.getSession(false) && session.usuario){
			def valorPuntaje = Integer.parseInt(params.rating)
			use(PuntuacionNumerica) {
				Puntuacion puntaje = valorPuntaje.estrellas
			}
			session.usuario puntuar (material, puntaje)
		}
		render(template: "/layouts/puntuacion", model: [material: material])
	}
}
