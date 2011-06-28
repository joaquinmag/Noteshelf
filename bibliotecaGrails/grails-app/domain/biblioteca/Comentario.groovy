package biblioteca

import biblioteca.usuario.Usuario

class Comentario implements Comparable{

	static belongsTo = [material:Material, autor:Usuario]
	
	String comentario
	Date dateCreated
	Date lastUpdated
	
	//Para levantar los comentarios de cada material al buscar y sus autores
	static searchable = {
		autor reference: true
	}
	
    static constraints = {
		comentario(blank:false,maxsize:255)
		material(display:false)
		autor(display:false)
    }

	public int compareTo(def other) {
		return dateCreated <=> other?.dateCreated // <=> es el operador compareTo en groovy
	}
}
