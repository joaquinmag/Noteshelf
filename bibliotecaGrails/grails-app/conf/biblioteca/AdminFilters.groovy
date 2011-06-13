package biblioteca

import biblioteca.usuario.Usuario

class AdminFilters {

	def emailConfirmationService
	def MailService
	def springSecurityService
	
    def filters = {
		
		
//		BorrarYEditarComentario(controller:'comentario', action:"(delete|edit)") {
//			before = {
//				if(request.getSession(false) && session.usuario){
//					if(session.usuario.admin){
//						return true
//					} else if(session?.usuario?.login != Comentario.get(params.id).autor.login) {
//						flash.message = "Este comentario no es tuyo."
//						redirect(controller:"material", action:"list")
//						return false
//					}
//				} else {
//					redirect(controller:"usuario", action:"login")
//					return false
//				}
//			}			
//		}
		
//		CreateComentario(controller:'comentario', action:'create') {
//			before = {
//				if(!session.usuario){
//					flash.message = "Deb&eacutes; iniciar sesi&oacute;n antes de comentar."
//					redirect(controller:"usuario", action:"login")
//					return false
//				}
//			}
//		}
	 	
//		SaveComentario(controller:'comentario', action:"(save)") {
//				before = {
//					if(request.getSession(false) && session.usuario){
//						def material = Material.get(params.material)
//						if(session.usuario.admin){
//							def comentario = new Comentario(autor:Usuario.findByLogin("admin"), material:material, comentario:params.comentario)
//							comentario.save(flush:true)
//							flash.message = "Comentario guardado."
//							redirect(controller:"material", action:"list")
//							return true
//						} else if(session.usuario.puedeComentar(material)) {
//							def comentario = new Comentario(autor:Usuario.findByLogin(params.autor), material:Material.get(params.material), comentario:params.comentario)
//							comentario.save(flush:true)
//							flash.message = "Comentario guardado."
//							redirect(controller:"material", action:"list")
//							return true
//						} else {
//							flash.message = "Nunca retiraste este material. No pod&eacute;s comentarlo."
//							redirect(controller:"material", action:"list")
//							return false
//						}
//					} else {
//						flash.message = "Deb&eacutes; iniciar sesi&oacute;n antes de comentar."
//						redirect(controller:"usuario", action:"login")
//						return false
//					}
//				}
//		}
		
//		UpdateComentario(controller:'comentario', action:"(update)") {
//			before = {
//				if(request.getSession(false) && session.usuario){
//					def comentario = Comentario.get(params.id)
//					comentario.comentario = params.comentario
//					if(session.usuario.admin){
//						comentario.save(flush:true)
//						flash.message = "Comentario actualizado."
//						redirect(controller:"material", action:"list")
//						return true
//					} else if(!(comentario in Comentario.findAllByAutor(session.usuario))) {
//						flash.message = "Este comentario no es tuyo. No pod&eacute;s modificarlo."
//						redirect(controller:"material", action:"list")
//						return false
//					} else {
//						comentario.save(flush:true)
//						flash.message = "Comentario actualizado."
//						redirect(controller:"material", action:"list")
//						return true
//					}
//				} else {
//					flash.message = "Deb&eacutes; iniciar sesi&oacute;n antes de modificar un comentario."
//					redirect(controller:"usuario", action:"login")
//					return false
//				}
//			}
//		}
    }
}
