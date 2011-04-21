package biblioteca


class Apunte extends Material{
	
	String nombre = ""
	String serie = ""
	String tema = ""
	
    static constraints = {
		serie(nullable:true)
    }

	String toString(){
		nombre+" "+serie
	}
}
