package biblioteca

import java.util.Calendar;
import java.util.GregorianCalendar;
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

	void definirFechaDeDevolucion(){
		Calendar startCal = Calendar.getInstance()
		
		startCal.setTime(new Date())
		def diasSumados = 0
		while (diasSumados < 2 || (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esFeriado(startCal))){
			
			startCal.add(Calendar.DAY_OF_MONTH, 1)
			if (!(startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esFeriado(startCal)))
				diasSumados++			
		}

		this.devolucion = startCal.getTime()
	}
	
	private boolean esFeriado(Calendar calendar){
		def feriados = Feriado.list()
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		
		for(int i=0 ; i<feriados.size() ; i++){
			def it = feriados.get(i)
			
			//Si es feriado
			if (it.dia == calendar.get(Calendar.DATE) && it.mes == calendar.get(Calendar.MONTH)+1)
				return true
			
			//Para averiguar el dia de la semana que cae el feriado
			cal.set(Calendar.DATE, it.dia)
			cal.set(Calendar.MONTH, it.mes-1)
			
			//Si es lunes puente
			if (it.nacional && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)){
				cal.add(Calendar.DATE, -1)
				if (cal.get(Calendar.DATE) == calendar.get(Calendar.DATE) && (cal.get(Calendar.MONTH)) == calendar.get(Calendar.MONTH))
					return true
			}
			
			//Para averiguar el dia de la semana que cae el feriado
			cal.set(Calendar.DATE, it.dia)
			cal.set(Calendar.MONTH, it.mes-1)
			
			//Si es viernes puente
			if (it.nacional && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)){
				cal.add(Calendar.DATE, 1)
				if (cal.get(Calendar.DATE) == calendar.get(Calendar.DATE) && (cal.get(Calendar.MONTH)) == calendar.get(Calendar.MONTH))
					return true
			}
		}
		
		return false
	}
}
