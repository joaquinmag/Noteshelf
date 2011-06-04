package biblioteca.usuario

import biblioteca.Material
import biblioteca.excepciones.UsuarioYaPuntuoException
import biblioteca.excepciones.MaterialNuncaFuePrestadoException

class RolCliente extends RolUsuario {
    
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
