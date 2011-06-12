package biblioteca
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService;

@Secured(['ROLE_USUARIO','ROLE_ADMIN'])
class LogoutController {

	def springSecurityService
	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index = {
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
	}
}
