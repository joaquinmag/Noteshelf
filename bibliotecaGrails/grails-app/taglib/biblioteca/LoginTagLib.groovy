package biblioteca

class LoginTagLib {
	def loginControl = {
		if(request.getSession(false) && session.usuario){
			out << "Hola ${session.usuario.login} "
			if (session.usuario.admin) {
				out << """[${link(action:"index",controller:"admin"){"Administrar"}}]"""
			} else {
				out << """[${link(action:"list",controller:"prestamo"){"Mis pr&eacute;stamos"}}]"""
			}
			out << """[${link(action:"edit",controller:"usuario",id:"${session.usuario.id}"){"Mis datos"}}]"""
			out << """[${link(action:"logout",controller:"usuario"){"Logout"}}]"""
		} else {
			out << """[${link(action:"login",controller:"usuario"){"Login"}}] [${link(action:"create",controller:"usuario"){"Registrarse"}}]"""
		}
	}
}