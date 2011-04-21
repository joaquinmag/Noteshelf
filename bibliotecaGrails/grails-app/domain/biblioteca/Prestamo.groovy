package biblioteca


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
		devolucion(nullable:false,validator: {return (it > new Date())})
    }
	
	static mapping = {
		materialPrestado fetch:"join"
	}
	
	static transients = ['vencido']
	boolean isVencido(){
		return (pendiente && Calendar.getInstance().getTime().after(devolucion))
	}
}
