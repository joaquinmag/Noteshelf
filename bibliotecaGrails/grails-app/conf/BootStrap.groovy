import grails.plugin.mail.MailService;
import grails.util.GrailsUtil
import biblioteca.usuario.Usuario
import biblioteca.usuario.UsuarioRol
import biblioteca.usuario.Rol
import biblioteca.Feriado

class BootStrap {
    
    def emailConfirmationService
	def springSecurityService

    def init = { servletContext ->
		
        emailConfirmationService.onConfirmation = { email, uid ->
            def usuario = Usuario.findAllByEmail(email)
            usuario.each {
                it.enabled = true
                it.save()
            }
            return [controller:'usuario', action:'confirmado']
        }
		
        emailConfirmationService.onInvalid = { uid ->
            def usuario = Usuario.findAllByEmail(email)
            usuario.delete();
            return [controller:'usuario', action:'confirmado']
        }
		
        emailConfirmationService.onTimeout = { email, uid ->
            def usuario = Usuario.findAllByEmail(email)
            usuario.delete();
            return [controller:'usuario', action:'confirmado']
        }
        
        def rolAdmin = new biblioteca.usuario.Rol(authority: 'ROLE_ADMIN').save(flush: true)
		def rolUsuario = new biblioteca.usuario.Rol(authority: 'ROLE_USUARIO').save(flush: true)
		String password = springSecurityService.encodePassword('pass')
		
		def adminUser = new Usuario(	username: 'admin', 
										email: 'bibliotecaapuntesfiuba@gmail.com', 
										enabled: true, 
										accountLocked: false, 
										accountExpired: false, 
										passwordExpired: false, 
										password: password)
		adminUser.save(flush: true)
		UsuarioRol.create adminUser, rolAdmin, true
		
		def simpleUser = new Usuario(	username: 'user',
										email: 'leo6987@gmail.com',
										enabled: true,
										accountLocked: false,
										accountExpired: false,
										passwordExpired: false,
										password: password)
		simpleUser.save(flush: true)
		UsuarioRol.create simpleUser, rolUsuario, true
		
		//Feriados nacionales
		def feriado = new Feriado(dia:1,mes:1,nacional:true,descripcion:"Año nuevo")
		feriado.save(flush:true)
		feriado = new Feriado(dia:7,mes:3,nacional:true,descripcion:"Carnaval")
		feriado.save(flush:true)
		feriado = new Feriado(dia:8,mes:3,nacional:true,descripcion:"Carnaval")
		feriado.save(flush:true)
		feriado = new Feriado(dia:24,mes:3,nacional:true,descripcion:"Día Nacional de la Memoria por la Verdad y la Justicia")
		feriado.save(flush:true)
		feriado = new Feriado(dia:2,mes:4,nacional:true,descripcion:"Día del Veterano y de los Caídos en la Guerra de Malvinas")
		feriado.save(flush:true)
		feriado = new Feriado(dia:22,mes:4,nacional:true,descripcion:"Viernes Santo")
		feriado.save(flush:true)
		feriado = new Feriado(dia:1,mes:5,nacional:true,descripcion:"Día del Trabajador")
		feriado.save(flush:true)
		feriado = new Feriado(dia:25,mes:5,nacional:true,descripcion:"Día de la Revolución de Mayo")
		feriado.save(flush:true)
		feriado = new Feriado(dia:20,mes:6,nacional:true,descripcion:"Paso a la Inmortalidad del General Manuel Belgrano")
		feriado.save(flush:true)
		feriado = new Feriado(dia:9,mes:7,nacional:true,descripcion:"Día de la Independencia")
		feriado.save(flush:true)
		feriado = new Feriado(dia:8,mes:12,nacional:true,descripcion:"Inmaculada Concepción de María")
		feriado.save(flush:true)
		feriado = new Feriado(dia:25,mes:12,nacional:true,descripcion:"Navidad")
		feriado.save(flush:true)
		
		//Feriados nacionales trasladables
		feriado = new Feriado(dia:22,mes:8,nacional:true,descripcion:"Paso a la Inmortalidad del General José de San Martín")
		feriado.save(flush:true)
		feriado = new Feriado(dia:10,mes:10,nacional:true,descripcion:"Día del Respeto a la Diversidad Cultural")
		feriado.save(flush:true)
		feriado = new Feriado(dia:28,mes:11,nacional:true,descripcion:"Día de la Soberanía Nacional")
		feriado.save(flush:true)
		
		//Asuetos y dias no laborables
		feriado = new Feriado(dia:21,mes:4,nacional:false,descripcion:"Jueves Santo")
		feriado.save(flush:true)
		feriado = new Feriado(dia:21,mes:9,nacional:false,descripcion:"Día del estudiante")
		feriado.save(flush:true)
		
    }

}
