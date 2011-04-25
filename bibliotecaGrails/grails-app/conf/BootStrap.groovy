import grails.plugin.mail.MailService;
import grails.util.GrailsUtil
import biblioteca.Usuario

class BootStrap {
    
    def emailConfirmationService

    def init = { servletContext ->
		
        emailConfirmationService.onConfirmation = { email, uid ->
            def usuario = biblioteca.Usuario.findAllByEmail(email)
            usuario.each {
                it.confirmado = true
                it.save()
            }
            return [controller:'usuario', action:'confirmado']
        }
		
        emailConfirmationService.onInvalid = { uid ->
            def usuario = biblioteca.Usuario.findAllByEmail(email)
            usuario.delete();
            return [controller:'usuario', action:'confirmado']
        }
		
        emailConfirmationService.onTimeout = { email, uid ->
            def usuario = biblioteca.Usuario.findAllByEmail(email)
            usuario.delete();
            return [controller:'usuario', action:'confirmado']
        }

        def admin = new Usuario(
            login: "groso",
            password: "groso",
            email: "joacomag@gmail.com",
            rol: "admin",
        )
        admin.save(flush:true)
        admin.confirmado = true
        admin.save(flush:true)
               
    }

}
