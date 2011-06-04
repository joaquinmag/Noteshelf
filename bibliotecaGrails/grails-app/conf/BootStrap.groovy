import grails.plugin.mail.MailService;
import grails.util.GrailsUtil
import biblioteca.usuario.Usuario
import biblioteca.usuario.RolAdministrador

class BootStrap {
    
    def emailConfirmationService

    def init = { servletContext ->
		
        emailConfirmationService.onConfirmation = { email, uid ->
            def usuario = Usuario.findAllByEmail(email)
            usuario.each {
                it.confirmado = true
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
        
        RolAdministrador rolAdministrador = new RolAdministrador()
        rolAdministrador.save(flush: true)

        def admin = new Usuario(
            login: "groso",
            password: "groso",
            email: "bibliotecaapuntesfiuba@gmail.com",
            rol: rolAdministrador,
        )
        admin.save(flush:true)
        admin.confirmado = true
        admin.save(flush:true)
    }

}
