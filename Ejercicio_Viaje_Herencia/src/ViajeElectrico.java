public class ViajeElectrico extends Viaje {
    //Constructor
    public ViajeElectrico(int cantidadVagones, int pasajerosMax, Trayecto trayecto){
        super(cantidadVagones, pasajerosMax, trayecto);
    }
    @Override
    public double tiempoDeDemora(){
        float d = trayecto.getDistanciaKm();
        int e = trayecto.getCantidadEstaciones();
        return (d*e)/2.0;
    }
}

