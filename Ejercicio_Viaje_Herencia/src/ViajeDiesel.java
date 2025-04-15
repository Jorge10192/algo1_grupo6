public class ViajeDiesel extends Viaje {
    //Constructor
    public ViajeDiesel(int cantidadVagones, int pasajerosMax, Trayecto trayecto){
        super(cantidadVagones, pasajerosMax, trayecto);
    }
    @Override
    public double tiempoDeDemora(){
        float d = trayecto.getDistanciaKm();
        int e = trayecto.getCantidadEstaciones();
        return (d*e)/2.0+(e+pasajerosMax)/10.0;
    }
}
