package biblioteca

class RolAdministrador extends RolUsuario {
    
    RolAdministrador() {
        nombre = "Administrador"
    }
    
    def verificarPosibilidadDePuntuar() {
        true
    }

    static constraints = {
    }
}
