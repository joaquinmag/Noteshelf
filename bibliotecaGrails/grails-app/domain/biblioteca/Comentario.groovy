package biblioteca

import biblioteca.usuario.Usuario

class Comentario implements Comparable{

	static belongsTo = [material:Material, autor:Usuario]
	
	String comentario
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
		comentario(blank:false,maxsize:255)
		material(display:false)
		autor(display:false)
    }

	public int compareTo(def other) {
		return dateCreated <=> other?.dateCreated // <=> es el operador compareTo en groovy
	}
}
