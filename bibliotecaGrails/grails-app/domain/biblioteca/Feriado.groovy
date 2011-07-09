package biblioteca

class Feriado {

	String descripcion
    int dia
	int mes
	boolean nacional

	static constraints = {
		dia(unique:'mes',nullable:false,range:1..31)
		mes(nullable:false,range:1..12)
	}
	
	public boolean validarFecha(){
		if ((mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10 || mes==12) && (dia<1 || dia>31))
			return false
		if ((mes==4 || mes==6 || mes==9 || mes==11) && (dia<1 || dia>30))
			return false
		if (mes==2 && (dia<1 || dia>29))
			return false
		return true
	}
}
