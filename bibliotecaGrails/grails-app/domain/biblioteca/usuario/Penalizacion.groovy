package biblioteca.usuario

import java.util.Date;
import biblioteca.Prestamo

class Penalizacion {
	
    static final long MILSEGS_POR_DIA = 24 * 60 * 60 * 1000

    Date fechaPenalizacion = Calendar.getInstance().getTime()
    Integer semanasPenalizacion = 0
    static belongsTo = [usuario:Usuario]
	
    static constraints = {
    }

	
    static transients = ['penalizado']
    boolean isPenalizado(){
        Calendar hoy = Calendar.getInstance();
        hoy.add(Calendar.DATE, -semanasPenalizacion*7);

        return hoy.getTime()<=fechaPenalizacion
    }

    void penalizar(Prestamo prestamo){
        this.fechaPenalizacion = prestamo.devolucionReal
        def tiempoDePenalizacion = prestamo.devolucionReal.getTime()-prestamo.devolucion.getTime()
        this.semanasPenalizacion = (tiempoDePenalizacion)/ MILSEGS_POR_DIA
    }

}
