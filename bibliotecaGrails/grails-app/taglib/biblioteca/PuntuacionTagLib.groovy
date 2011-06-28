package biblioteca

class PuntuacionTagLib {

	def springSecurityService
	
	def mostrarPuntuacion = {attrs, body->
		def material = attrs['material']
		def usuario = springSecurityService.currentUser
		if (springSecurityService.loggedIn && material.fuePuntuadoPor(usuario)){
			out<<body()+Puntuacion.findByMaterialAndAutor(material,usuario).puntaje
		}
	}

	def puedePuntuar = {attrs, body->
		if (verificarPosibilidadDePuntuar(attrs['material'])){
			out<< body()
		}
	}
	
	def noPuedePuntuar = {attrs, body->
		if (!verificarPosibilidadDePuntuar(attrs['material'])){
			out<< body()
		}
	}
	
	private boolean verificarPosibilidadDePuntuar(Material material){
		def usuario = springSecurityService.currentUser
		return (springSecurityService.loggedIn && usuario.puedePuntuar(material))
	}
}
