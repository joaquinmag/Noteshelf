package biblioteca.usuario

import java.util.Calendar;
import biblioteca.Material
import biblioteca.Puntuacion
import biblioteca.Prestamo
import biblioteca.Comentario

class Usuario {

	String username
	String password
	String email
	Boolean enabled
	Boolean accountLocked
	Boolean accountExpired
	Boolean passwordExpired
	Penalizacion penalizacion = new Penalizacion()
	
	static constraints = {
		username blank: false, unique: true, nullable:false
		password blank: false, password: true
		email blank: false, unique: true, email: true
		enabled display: false
		accountLocked display: false
		accountExpired display: false
		passwordExpired display: false
		penalizacion display: false
	}
	//Para levantar el autor de los comentarios de cada material al buscar
	static searchable = true
	
	static mapping = {
		password column: '`password`'
		prestamos cascade:'all-delete-orphan'
	}
	
	static hasMany = [prestamos:Prestamo, comentarios:Comentario, puntuaciones:Puntuacion]
	static fetchMode = [puntuaciones:'eager', prestamos:'eager']
			
	Set<Rol> getAuthorities() {
		UsuarioRol.findAllByUsuario(this).collect { it.rol } as Set
	}

	String toString()
	{ username }

	boolean puedeComentar(Material material){
		if (prestamos.size() == 0)
			return false
		else
			return (material.id in prestamos*.materialPrestado*.id)
	}
	
	boolean tienePrestamosPendientes(){
		def cont = 0
		
		prestamos.each{
			if (it.pendiente)
				cont++
		}
		
		return (cont>=1)
    }

	static transients = ['penalizado']
	boolean isPenalizado(){
		return this.penalizacion?.isPenalizado()
	}
	
	void penalizar(Prestamo prestamo){
		penalizacion.penalizar(prestamo)
	}
        
	private boolean puedePuntuar(Material material){
		if (this.getAuthorities().contains(Rol.findByAuthority("ROLE_ADMIN")))
			return true
		else if (this.prestamos.size() > 0){
			if (this.prestamos*.materialPrestado*.id.contains(material.id))
				return true
		}
		return false
	}
}
