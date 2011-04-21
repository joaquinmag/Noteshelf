package biblioteca

import java.util.Calendar;

class Usuario {
	
	String login
	String password
	String email
	String rol = "cliente"
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
		return rol == "admin"
	}

	boolean isPenalizado(){
		
		Calendar hoy = Calendar.getInstance();
		hoy.add(Calendar.DATE, -semanasPenalizacion*7);

		return hoy.getTime()<=fechaPenalizacion
	}
	
	boolean puedePuntuar(Material material){
		return (((!(this in material.puntuaciones*.autor)) && (material.id in this.prestamos*.materialPrestado*.id)) || this.admin)
	}
	
	def beforeInsert = {
		password = password.encodeAsSHA()
		confirmado = false
	}
}
