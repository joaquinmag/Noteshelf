import grails.plugin.mail.MailService;
import grails.util.GrailsUtil
import biblioteca.usuario.Usuario
import biblioteca.usuario.UsuarioRol
import biblioteca.usuario.Rol

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
		
		assert Usuario.count() == 2
		assert Rol.count() == 2
		assert UsuarioRol.count() == 2
    }

}
