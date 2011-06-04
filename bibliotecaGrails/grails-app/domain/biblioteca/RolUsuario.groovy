package biblioteca

class RolUsuario {
    
    String nombre
   
    static belongsTo = [ usuario : Usuario ]
    
    def verificarPosibilidadDePuntuar() {
        throw new UnsupportedOperationException()
    }
    
    static constraints = {
        nombre(nullable:false)
    }
}
