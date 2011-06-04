package biblioteca

class MaterialController {

	def scaffold = true
	
	def buscar = {
		flash.message = "Resultados de la b&uacute;squeda para: ${params.q}"
		
		params.q = params.q.replace ('á','a');
		params.q = params.q.replace ('é','e');
		params.q = params.q.replace ('í','i');
		params.q = params.q.replace ('ó','o');
		params.q = params.q.replace ('ú','u');
		params.q = params.q.replace ('Á','A');
		params.q = params.q.replace ('É','E');
		params.q = params.q.replace ('Í','I');
		params.q = params.q.replace ('Ó','o');
		params.q = params.q.replace ('Ú','U');
		def resultsMap = Material.search(params.q, params)

		def resultados = new ArrayList()
		resultsMap.results.each{
			resultados.add(Material.get(it.id))
		}

		render(view:'list',
			model:[materialInstanceList:resultados,materialInstanceTotal:Material.countHits(params.q)])
	}
	
	def searchAJAX = {
		def materiales = Apunte.findAllByAutorLikeOrNombreLikeOrSerieLikeOrTemaLike("%${params.query}%","%${params.query}%","%${params.query}%","%${params.query}%")
		materiales.addAll(Cuaderno.findAllByAutorLikeOrCodigoMateriaLikeOrMateriaLikeOrCatedraLike("%${params.query}%","%${params.query}%","%${params.query}%","%${params.query}%","%${params.query}%"))
		materiales.addAll(Resumen.findAllByAutorLikeOrCodigoMateriaLikeOrMateriaLikeOrDescripcionLike("%${params.query}%","%${params.query}%","%${params.query}%","%${params.query}%","%${params.query}%"))

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
