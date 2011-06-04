package biblioteca

import java.util.Calendar;

class Usuario {
	
	String login
	String password
	String email
        RolUsuario rol
	Boolean confirmado = false
	Date fechaPenalizacion
	Integer semanasPenalizacion = 0
	
	String toString()
	{ login }
	
	static hasMany = [prestamos:Prestamo, comentarios:Comentario, puntuaciones:Puntuacion]
	static fetchMode = [puntuaciones:'eager', prestamos:'eager']
	
	static constraints = {
		login(blank:false, nullable:false, unique:true)
		password(blank:false, password:true)
		email(blank:false,email:true)
		rol(inList:["cliente", "admin"], display:false)
		confirmado(display:false)
		fechaPenalizacion(nullable:true)
	}
		
	static transients = ['admin','penalizado']
	boolean isAdmin(){
		return rol.isAdmin()
	}

	boolean isPenalizado(){
		
		Calendar hoy = Calendar.getInstance();
		hoy.add(Calendar.DATE, -semanasPenalizacion*7);

		return hoy.getTime() <= fechaPenalizacion
	}
        
	private void puedePuntuar(Material material){
            rol.verificarPosibilidadDePuntuar()
	}
        
        def puntuar(Material material, Puntuacion puntaje) {
            puedePuntuar material
            puntaje.por this
            puntaje.de material
            material.puntuar puntuaje
        }
	
	def beforeInsert = {
		password = password.encodeAsSHA()
		confirmado = false
	}
}
