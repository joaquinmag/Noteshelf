package biblioteca

import biblioteca.usuario.Usuario

class Prestamo {

	Material materialPrestado
	Usuario usuario
	Date dateCreated
	Date devolucion
	Date devolucionReal
	boolean pendiente = true
	
    static constraints = {
		devolucionReal(display:false, nullable:true)
		pendiente(display:false)
		usuario(nullable:false)
		materialPrestado(nullable:false)
		devolucion(nullable:false)
    }
	
	static mapping = {
		materialPrestado fetch:"join"
	}
	
	static transients = ['vencido']
	boolean isVencido(){
		return (pendiente && Calendar.getInstance().getTime().after(devolucion))
	}
	
	boolean debePenalizar(){
		return this.devolucion.before(this.devolucionReal)
	}
}
