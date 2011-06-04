package biblioteca

import biblioteca.excepciones.UsuarioYaPuntuoException
import biblioteca.excepciones.MaterialNuncaFuePrestadoException

class RolCliente extends RolUsuario {
    
    RolCliente() {
        this.nombre = "Cliente"
    }
    
    def verificarPosibilidadDePuntuar(Material material) {
        if (this in material.puntuaciones*.autor) {
            throw new UsuarioYaPuntuoException("El usuario ya puntuo este material")
        }

        if (material.id in usuario.prestamos*.materialPrestado*.id) {
            throw new MaterialNuncaFuePrestadoException()
        }
        
        true
    }

    static constraints = {
    }
}
