package biblioteca

class RolAdministrador extends RolUsuario {
    
    def verificarPosibilidadDePuntuar(Material material) {
        true
    }

    static constraints = {
    }
}
