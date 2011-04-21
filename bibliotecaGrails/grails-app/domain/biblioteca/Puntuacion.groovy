package biblioteca

class Puntuacion {

	Integer puntaje
	
	static belongsTo = [material:Material, autor:Usuario]

    static constraints = {
		puntaje(range:0..5)
    }
}
