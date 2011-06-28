package biblioteca

import biblioteca.usuario.Usuario


class Material {

	String autor
	Date dateCreated
	SortedSet comentarios

	//Para levantar los comentarios de cada material al buscar
	static searchable = {
		comentarios reference: true
	}

	static hasMany = [prestamos:Prestamo,comentarios:Comentario,puntuaciones:Puntuacion]
	static fetchMode = [prestamos:'eager']
	
	static constraints = {
		autor(nullable:true)
    }
	
    public void puntuar(Puntuacion puntuacion) {
		puntuaciones.add(puntuacion)
    }

	public boolean fuePuntuadoPor(Usuario usuario){
		return this.puntuaciones*.autor.contains(usuario)
	}

	static transients = ['puntuacion']
	Float getPuntuacion() {
		def total = 0
		def acum = 0
		puntuaciones.each{
			total+=it.puntaje
			acum++
		}
		if (acum == 0)
			return 0
		else
			return (total/acum) 
	}
}
