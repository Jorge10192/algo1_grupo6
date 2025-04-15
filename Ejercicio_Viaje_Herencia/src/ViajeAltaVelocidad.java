public class ViajeAltaVelocidad extends Viaje {
    //Constructor
    public ViajeAltaVelocidad(int cantidadVagones, int pasajerosMax, Trayecto trayecto){
        super(cantidadVagones, pasajerosMax, trayecto);
    }
    @Override
    public double tiempoDeDemora(){
        float d = trayecto.getDistanciaKm();
        return d/10.0;
    }
}