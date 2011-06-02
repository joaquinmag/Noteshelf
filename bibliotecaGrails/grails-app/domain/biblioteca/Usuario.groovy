package biblioteca

import java.util.Calendar;

class Usuario {

	String login
	String password
	String email
	String rol = "cliente"
	Boolean confirmado = false
	Penalizacion penalizacion = new Penalizacion()
	
		
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
		penalizacion(display:false)
	}
		
	static transients = ['admin','penalizado']
	boolean isAdmin(){
		return rol == "admin"
	}
	
	boolean puedePuntuar(Material material){
		return (((!(this in material.puntuaciones*.autor)) && (material.id in this.prestamos*.materialPrestado*.id)) || this.admin)
	}
	
	boolean puedeComentar(Material material){
		return (material.id in prestamos*.materialPrestado*.id)
	}
	
	boolean tienePrestamosPendientes(){
		def cont = 0
		
		prestamos.each {
			if (it.pendiente)
				cont++
		}
		return (cont >= 1)
	}

	boolean isPenalizado(){
		return this.penalizacion.isPenalizado()
	}
	
	void penalizar(Prestamo prestamo){
		penalizacion.penalizar(prestamo)
	}
	
	def beforeInsert = {
		password = password.encodeAsSHA()
		confirmado = false
	}
}
