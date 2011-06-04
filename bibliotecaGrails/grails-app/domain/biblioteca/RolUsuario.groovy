package biblioteca

class RolUsuario {
    
    String nombre

    def verificarPosibilidadDePuntuar(Material material) {
        // no se pueden hacer abstract en las clases de dominio de grails.
        // esto se arregla en grails 1.4
        throw new UnsupportedOperationException()
    }
    
    static belongsTo = [ Usuario: usuario ]
    
    static constraints = {
        nombre(nullable:false)
    }
}
