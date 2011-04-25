import grails.plugin.mail.MailService;
import grails.util.GrailsUtil

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

}
