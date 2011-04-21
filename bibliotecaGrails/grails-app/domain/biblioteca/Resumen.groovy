package biblioteca

class Resumen extends Material{

	String codigoMateria
	String materia
	String descripcion
	
    static constraints = {
    }
	
	String toString(){
		descripcion+" "+materia
	}
}
