package biblioteca

class Puntuacion {

    Integer puntaje
    String unit
    
    static belongsTo = [material:Material, autor:Usuario]

    def por(Usuario autor) {
        this.autor = autor
    }
    
    def de(Material material) {
        this.material = material
    }
    
    static constraints = {
        puntaje(range:0..5)
    }

}

// Hago este DSL solo con fines demostrativos :P
@Category(Number)
class PuntuacionNumerica {
    def get(String unit) {
        new Puntuacion(this, unit, null, null)
    }
}
