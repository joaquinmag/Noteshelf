package biblioteca.usuario

import biblioteca.Material

class RolAdministrador extends RolUsuario {
    
    def verificarPosibilidadDePuntuar(Material material) {
        true
    }

    static constraints = {
    }
}
