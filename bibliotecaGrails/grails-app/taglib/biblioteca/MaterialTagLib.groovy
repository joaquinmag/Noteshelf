package biblioteca

class MaterialTagLib {
	
	def springSecurityService

	def materialPorId = {attrs, body->
		out << Material.get(attrs['id']).toString() 
	}
	
	def borrarOEditarComentario = {attrs, body->
			if (springSecurityService.loggedIn && (('ROLE_ADMIN' in springSecurityService.principal.authorities*.toString()) || (springSecurityService.currentUser.username == Comentario.get(attrs['comentario']).autor.username))) {
				out << """[${link(action:"delete",controller:"comentario",id:attrs['comentario']){"Borrar"}}]"""
				out << """[${link(action:"edit",controller:"comentario",id:attrs['comentario']){"Editar"}}]"""
			}
	}

	def comentarMaterial = {attrs, body->
		out << """[${link(action:"create",params:[material:attrs['material']],controller:"comentario"){"Comentar"}}]"""
	}

}
