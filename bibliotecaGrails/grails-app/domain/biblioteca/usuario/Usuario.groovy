package biblioteca.usuario

import java.util.Calendar;
import biblioteca.Material
import biblioteca.Puntuacion
import biblioteca.Prestamo
import biblioteca.Comentario

class Usuario {

	String login
	String password
	String email
        RolUsuario rol
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
	
	boolean puedeComentar(Material material){
		return (material.id in prestamos*.materialPrestado*.id)
	}
	
	boolean tienePrestamosPendientes(){
		def cont = 0
        }

	boolean isPenalizado(){
		return this.penalizacion.isPenalizado()
	}
	
	void penalizar(Prestamo prestamo){
		penalizacion.penalizar(prestamo)
	}
        
	private void puedePuntuar(Material material){
            rol.verificarPosibilidadDePuntuar(material)
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
