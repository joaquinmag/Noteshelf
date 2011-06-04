package biblioteca

abstract class RolUsuario {
    
    String nombre

    abstract boolean isAdmin()
    
    static belongsTo = Usuario
    
    static constraints = {
        nombre(nullable:false)
    }
}
