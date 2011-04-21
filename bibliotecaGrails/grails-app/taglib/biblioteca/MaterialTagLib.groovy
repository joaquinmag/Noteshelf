package biblioteca

class MaterialTagLib {

	def materialPorId = {attrs, body->
		out << Material.get(attrs['id']).toString() 
	}
	
	def borrarOEditarComentario = {attrs, body->
			if ((session?.usuario?.admin) || (session?.usuario?.login == Comentario.get(attrs['comentario']).autor.login)) {
				out << """[${link(action:"delete",controller:"comentario",id:attrs['comentario']){"Borrar"}}]"""
				out << """[${link(action:"edit",controller:"comentario",id:attrs['comentario']){"Editar"}}]"""
			}
	}

	def comentarMaterial = {attrs, body->
		out << """[${link(action:"create",params:[material:attrs['material']],controller:"comentario"){"Comentar"}}]"""
	}

}
