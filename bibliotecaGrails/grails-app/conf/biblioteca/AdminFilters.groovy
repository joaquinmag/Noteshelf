package biblioteca

class AdminFilters {

	def emailConfirmationService
	def MailService
	
    def filters = {
        AdminsOnly(controller:"(apunte|cuaderno|resumen|material)", action:"(create|edit|update|delete|save)") {
           before = {
			   if(!session?.usuario?.admin){
				   flash.message = "Solo para administradores"
				   redirect(controller:"material", action:"list")
				   return false
			   }
		   }
        }
		
		SaveUsuario(controller:'usuario', action:"(save)") {
			before = {
				params.rol = "cliente"
				params.fechaPenalizacion =  Calendar.getInstance().getTime()
				def usuario = new Usuario()
				usuario.properties = params

				usuario.validate()
				if (!usuario.hasErrors()) {
					emailConfirmationService.sendConfirmation(params.email,
							"Biblioteca de apuntes", [from:'leo6987@gmail.com',view:"/usuario/email", 
								usuarioInstance:usuario])
					flash.message = "Por favor confirm&aacute; tu registro desde tu direcci&oacute;n de e-mail."
				}
				render(view:'list', controller:'material')
			}
		}
		
		EditUsuario(controller:'usuario', action:"(edit)") {
			before = {
				if(request.getSession(false) && session.usuario) {
					// Si solo pone usuario/edit
					if (params.id == null) {
						redirect(controller:"material", action:"list")
						return false
					}
					
					if (session.usuario.login == Usuario.get(params.id).login) {
						flash.message = "Si modific&aacute;s tu direcci&oacute;n de e-mail tendr&aacute;s que confirmarla nuevamente."
						return true
					} else {
						redirect(controller:"material", action:"list")
						flash.message = "S&oacute;lo pod&eacute;s modificar tus propios datos."
						return false
					}
				}
				else {
					flash.message = "Inici&aacute; sesi&oacute;n para modificar tus datos."
					redirect(controller:"usuario", action:"login")
					return false
				}
			}
		}
		
		UpdateUsuario(controller:'usuario', action:"(update)") {
			before = {
				if(request.getSession(false) && session.usuario){
					def usuario = Usuario.get(params.id)
					String emailAnterior = session.usuario.email
					
					if (params.password != usuario.password)
						params.password = params.password.encodeAsSHA()

					usuario.properties = params
					
					if (usuario.save(flush:true)) {
						String confirmarMail = "" 
						if(emailAnterior != params['email']) {
							usuario.confirmado = false
							emailConfirmationService.sendConfirmation(params.email,
								"Biblioteca de apuntes", [from:'leo6987@gmail.com',view:"/usuario/emailUsuarioModificado"])
							confirmarMail = "Por favor confirm&aacute; tu registro desde tu direcci&oacute;n de e-mail."
						}
						
						flash.message = "Datos actualizados correctamente! "+confirmarMail
						session.usuario = usuario
						redirect(controller:"material", action:"list")
						return true
					}
				} else {
					flash.message = "Inici&aacute; sesi&oacute;n para modificar tus datos."
					redirect(controller:"usuario", action:"login")
					return false
				}
			}
		}

		  
		ProtegerUsuarios(controller:'usuario', action:"(list|show)") {
			before = {
				if(!session?.usuario?.admin){
					flash.message = "Solo para administradores"
					redirect(controller:"material", action:"list")
					return false
				}
			}
		}
		
		DeleteUsuarioYPrestamo(controller:"(usuario|prestamo)", action:"(delete)") {
			before = {
				flash.message = "No se pueden eliminar usuarios ni pr&eacute;stamos."
				redirect(controller:"material", action:"list")
				return false
			}
		}
		
		DevolverPrestamo(controller:"(prestamo)", action:"(devolver)") {
			before = {
				if(!session?.usuario?.admin){
					flash.message = "Solo para administradores"
					redirect(controller:"material", action:"list")
					return false
				} else {
					return true
				}
			}
		}
		
		SavePrestamo(controller:"(prestamo)", action:"(save)") {
			before = {
				if(!session?.usuario?.admin){
					flash.message = "Solo para administradores"
					redirect(controller:"material", action:"list")
					return false
				} else {

					def usuario = Usuario.findByLogin(params.usuario)
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
						if (Prestamo.findAllByUsuarioAndPendiente(usuario,true).size() >= 1) {
							flash.message = "El usuario tiene pr&eacute;stamos pendientes."
							redirect(controller:"prestamo", action:"list")
							return false
						} //Penalizacion vigente
							else if (usuario.penalizado) {
							flash.message = "El usuario tiene una penalizaci&oacute;n vigente."
							redirect(controller:"prestamo", action:"list")
							return false
						} //email no confirmado
							else if (!usuario.confirmado) {
							flash.message = "El usuario no confirm&oacute; su email."
							redirect(controller:"prestamo", action:"list")
							return false
						} else {
							prestamo.pendiente = true

							mailService.sendMail {
								to prestamo.usuario.email
								subject "Nuevo prestamo"
								body (controller:"prestamo", view:"email", model: [prestamoInstance : prestamo])
							}
						}
					}
				}
			}
		 }
		
		CreatePrestamo(controller:"(prestamo)", action:"(create)") {
			before = {
				if(!session?.usuario?.admin){
					flash.message = "Solo para administradores"
					redirect(controller:"material", action:"list")
					return false
				}
			}
		 }
		
		ShowPrestamo(controller:'prestamo', action:"(show)") {
			before = {
				if(request.getSession(false) && session.usuario){
					if(session.usuario.admin){
						return true
					} else {
						def prestamoAVer = Prestamo.get(params.id)
						def prestamosPropios = Prestamo.findAllByUsuario(session.usuario)
						if (!(prestamoAVer in prestamosPropios)) {
							flash.message = "Este pr&eacute;stamo no es tuyo."
							redirect(controller:"material", action:"list")
							return false
						}
						else
							return true
					}
				}
			}
		}
		
		BorrarYEditarComentario(controller:'comentario', action:"(delete|edit)") {
			before = {
				if(request.getSession(false) && session.usuario){
					if(session.usuario.admin){
						return true
					} else if(session?.usuario?.login != Comentario.get(params.id).autor.login) {
						flash.message = "Este comentario no es tuyo."
						redirect(controller:"material", action:"list")
						return false
					}
				} else {
					redirect(controller:"usuario", action:"login")
					return false
				}
			}			
		}
		
		CreateComentario(controller:'comentario', action:'create') {
			before = {
				if(!session.usuario){
					flash.message = "Deb&eacutes; iniciar sesi&oacute;n antes de comentar."
					redirect(controller:"usuario", action:"login")
					return false
				}
			}
		}
	 	
		SaveComentario(controller:'comentario', action:"(save)") {
				before = {
					if(request.getSession(false) && session.usuario){
						if(session.usuario.admin){
							def comentario = new Comentario(autor:Usuario.findByLogin("admin"), material:Material.get(params.material), comentario:params.comentario)
							comentario.save(flush:true)
							flash.message = "Comentario guardado."
							redirect(controller:"material", action:"list")
							return true
						} else if(!(Material.get(params.material) in Prestamo.findAllByUsuario(session.usuario).materialPrestado)) {
							flash.message = "Nunca retiraste este material. No pod&eacute;s comentarlo."
							redirect(controller:"material", action:"list")
							return false
						} else {
							def comentario = new Comentario(autor:Usuario.findByLogin(params.autor), material:Material.get(params.material), comentario:params.comentario)
							comentario.save(flush:true)
							flash.message = "Comentario guardado."
							redirect(controller:"material", action:"list")
							return true
						}
					} else {
						flash.message = "Deb&eacutes; iniciar sesi&oacute;n antes de comentar."
						redirect(controller:"usuario", action:"login")
						return false
					}
				}
		}
		
		UpdateComentario(controller:'comentario', action:"(update)") {
			before = {
				if(request.getSession(false) && session.usuario){
					def comentario = Comentario.get(params.id)
					comentario.comentario = params.comentario
					if(session.usuario.admin){
						comentario.save(flush:true)
						flash.message = "Comentario actualizado."
						redirect(controller:"material", action:"list")
						return true
					} else if(!(comentario in Comentario.findAllByAutor(session.usuario))) {
						flash.message = "Este comentario no es tuyo. No pod&eacute;s modificarlo."
						redirect(controller:"material", action:"list")
						return false
					} else {
						comentario.save(flush:true)
						flash.message = "Comentario actualizado."
						redirect(controller:"material", action:"list")
						return true
					}
				} else {
					flash.message = "Deb&eacutes; iniciar sesi&oacute;n antes de modificar un comentario."
					redirect(controller:"usuario", action:"login")
					return false
				}
			}
		}
    }
}
