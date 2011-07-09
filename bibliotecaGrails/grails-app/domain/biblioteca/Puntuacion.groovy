package biblioteca

import biblioteca.usuario.Usuario

class Puntuacion {

    Integer puntaje
    
	//Para levantar las puntuaciones al buscar los materiales
	static searchable = {
		autor reference: true
	}
	
    static belongsTo = [material:Material, autor:Usuario]

    def por(Usuario autor) {
        this.autor = autor
    }
    
    def de(Material material) {
        this.material = material
    }
    
    static constraints = {
        puntaje(range:0..5)
    }
}
