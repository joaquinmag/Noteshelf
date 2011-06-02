package biblioteca

class ParsearExcelService {

    static transactional = true

	/**
	* Creates a new authentication token using the username
	* and password parameters supplied by the client application
	*/
    static createAuthToken(){
	    def url = new URL("https://www.google.com/accounts/ClientLogin")
	    def connection = url.openConnection()
	
	    def queryString = "accountType=HOSTED_OR_GOOGLE&Email=bibliotecaapuntesfiuba@gmail.com" +
							"&Passwd=PAAAASSSSS&service=wise&source=biblioteca-apuntes-1.0"
	
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
   static String processRequest(connection, dataString){
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
   
   static String hacerRequest(connection){
	   connection.setRequestMethod("GET")
	   connection.doInput = true
	   //Reader reader = new InputStreamReader(connection.inputStream)
	   
//	   connection.connect()
		   
	   if (connection.responseCode == 200 || connection.responseCode == 201)
		   return connection.content.text

	   return "Error"
   }
   
   static obtenerArchivo(authToken){
	   def url = new URL("https://spreadsheets.google.com/feeds/list/0AjuFxyFChmdLcGRYMFJTTmhZX3p3Nm5QNkw0aHl5Smc/od7/private/full")
	   //od4 resumenes
	   //od6 Apuntes
	   def connection = url.openConnection()
	   connection.setRequestProperty("Authorization", "GoogleLogin auth=${authToken}")
	   
	   def returnMessage = new XmlSlurper().parseText(hacerRequest(connection))
	   //hacerRequest(connection).replaceAll()
//	   <gsx:listadodecuadernos>Codigo Materia</gsx:listadodecuadernos>
//	   <gsx:_cokwr>Materia</gsx:_cokwr>
//	   <gsx:_cre1l>Catedra</gsx:_cre1l>
//	   <gsx:_ciyn3>Cuatrimestre de Cursada</gsx:_ciyn3>
//	   <gsx:_ckd7g>Autor</gsx:_ckd7g>
//	   <gsx:_cztg3>Tipo</gsx:_cztg3>

    }
}
