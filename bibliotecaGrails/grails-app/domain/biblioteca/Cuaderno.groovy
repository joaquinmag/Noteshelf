package biblioteca


class Cuaderno extends Material{

	String codigoMateria
	String materia
	String catedra
	String cuatrimestre
	String tipo
	
    static constraints = {
    }
	
	String toString(){
		"cuaderno "+materia
	}
}
