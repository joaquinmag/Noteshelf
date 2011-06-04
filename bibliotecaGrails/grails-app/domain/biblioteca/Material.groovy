package biblioteca

import biblioteca.usuario.Usuario


class Material {

	String autor
	Date dateCreated
	SortedSet comentarios

	static searchable = true
	static hasMany = [prestamos:Prestamo,comentarios:Comentario,puntuaciones:Puntuacion]
	static fetchMode = [prestamos:'eager']
	
	static constraints = {
		autor(nullable:true)
    }
	
	Boolean puntuar(Integer puntaje, Usuario autor){
		puntuaciones.add(new Puntuacion(puntaje:puntaje,autor:autor,material:this))
	}
        
        def puntuar(Puntuacion puntuacion) {
            puntuaciones.add(puntuacion)
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
