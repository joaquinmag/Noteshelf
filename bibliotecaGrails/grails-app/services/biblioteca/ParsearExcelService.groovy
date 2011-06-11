package biblioteca

class ParsearExcelService {

    static transactional = true

	/**
	* Creates a new authentication token
	*/
    private static createAuthToken(){
	    def url = new URL("https://www.google.com/accounts/ClientLogin")
	    def connection = url.openConnection()
	
	    def queryString = "accountType=HOSTED_OR_GOOGLE&Email=bibliotecaapuntesfiuba@gmail.com" +
							"&Passwd=seminario1&service=wise&source=biblioteca-apuntes-1.0"
	
	    def returnMessage = processRequest(connection, queryString)

	    if(returnMessage != "Error"){
	        //the authtentication token
	        return returnMessage.split(/Auth=/)[1].trim()
	    }
	}
	
	/**
	* POSTs the data messages in the request body to the
	* supplied connection.  This method also checks to ensure that
	* the response code is correct.  If the response code is something
	* other than what is expected (200 or 201) then an "Error" string
	* is returned.
	*/
   private static String processRequest(connection, dataString){
	   connection.setRequestMethod("POST")
	   connection.doOutput = true
	   Writer writer = new OutputStreamWriter(connection.outputStream)
	   writer.write(dataString)
	   writer.flush()
	   writer.close()
	   connection.connect()

	   if (connection.responseCode == 200 || connection.responseCode == 201)
		   return connection.content.text
			   
	   return "Error"
   }
   
   private static String doRequest(connection){
	   connection.setRequestMethod("GET")
	   connection.doInput = true
		   
	   if (connection.responseCode == 200 || connection.responseCode == 201)
		   return connection.content.text

	   return "Error"
   }
   
   static List<Cuaderno> obtenerCuadernos(){
	   def authToken = ParsearExcelService.createAuthToken()
	   
	   def url = new URL("https://spreadsheets.google.com/feeds/list/0AjuFxyFChmdLcGRYMFJTTmhZX3p3Nm5QNkw0aHl5Smc/od7/private/full")
	   def connection = url.openConnection()
	   connection.setRequestProperty("Authorization", "GoogleLogin auth=${authToken}")
	   
	   def returnMessage = new XmlSlurper().parseText(doRequest(connection)).declareNamespace(gsx: 'http://schemas.google.com/spreadsheets/2006/extended')
	   
	   def cuadernos = []
	   
	   def i = 0	   
	   returnMessage.entry.each{

		   if (i>0){
			   
			   def codigoMateria = new String(it.'title'.toString().getBytes(), "UTF-8")
			   def materia = new String(it.'_cokwr'.toString().getBytes(), "UTF-8")
			   def catedra = new String(it.'_cre1l'.toString().getBytes(), "UTF-8")
			   def cuatrimestre = new String(it.'_ciyn3'.toString().getBytes(), "UTF-8")
			   def tipo = new String(it.'_cztg3'.toString().getBytes(), "UTF-8")
			   def autor = new String(it.'_ckd7g'.toString().getBytes(), "UTF-8")

			   def cuad = new Cuaderno(codigoMateria:codigoMateria,materia:materia,catedra:catedra,cuatrimestre:cuatrimestre,tipo:tipo,autor:autor)

			   cuadernos.add(cuad)
		   }
		   i++;
   		}

	   return cuadernos
    }
   
   
   static List<Resumen> obtenerResumenes(){
	   def authToken = ParsearExcelService.createAuthToken()
	   
	   def url = new URL("https://spreadsheets.google.com/feeds/list/0AjuFxyFChmdLcGRYMFJTTmhZX3p3Nm5QNkw0aHl5Smc/od4/private/full")

	   def connection = url.openConnection()
	   connection.setRequestProperty("Authorization", "GoogleLogin auth=${authToken}")
	   
	   def returnMessage = new XmlSlurper().parseText(doRequest(connection))//.declareNamespace(gsx: 'http://schemas.google.com/spreadsheets/2006/extended')
	   
	   def resumenes = []

	   def i = 0
	   returnMessage.entry.each{

		   if (i>0){
			   def codigoMateria = new String(it.'title'.toString().getBytes(), "UTF-8")
			   def materia = new String(it.'_cokwr'.toString().getBytes(), "UTF-8")
			   def descripcion = new String(it.'_cre1l'.toString().getBytes(), "UTF-8")
			   def autor = new String(it.'_ciyn3'.toString().getBytes(), "UTF-8")

			   def resumen = new Resumen(codigoMateria:codigoMateria,materia:materia,descripcion:descripcion,autor:autor)

			   resumenes.add(resumen)
		   }
			   
		   i++;
	   	}

	   return resumenes
	}

   
   static List<Apunte> obtenerApuntes(){
	   def authToken = ParsearExcelService.createAuthToken()
	   
	   def url = new URL("https://spreadsheets.google.com/feeds/list/0AjuFxyFChmdLcGRYMFJTTmhZX3p3Nm5QNkw0aHl5Smc/od6/private/full")

	   def connection = url.openConnection()
	   connection.setRequestProperty("Authorization", "GoogleLogin auth=${authToken}")
	   
	   def returnMessage = new XmlSlurper().parseText(doRequest(connection))//.declareNamespace(gsx: 'http://schemas.google.com/spreadsheets/2006/extended')
	   
	   def apuntes = []

	   returnMessage.entry.each{

		   def nombre = new String(it.'apunte'.toString().getBytes(), "UTF-8")
		   def serie = new String(it.'serie'.toString().getBytes(), "UTF-8")
		   def autor = new String(it.'autor'.toString().getBytes(), "UTF-8")
		   def tema = new String(it.'tema'.toString().getBytes(), "UTF-8")
		   
		   def apunte = new Apunte(nombre:nombre,serie:serie,tema:tema,autor:autor)

		   apuntes.add(apunte)
	   }

	   return apuntes
   }

}
