package viaje_b;

public class Trayecto {
    private int distanciaKm;
    private int cantidadEstaciones;

    public Trayecto(int distanciaKm, int cantidadEstaciones) {
        this.distanciaKm = distanciaKm;
        this.cantidadEstaciones = cantidadEstaciones;
    }

    public int getDistanciaKm() {
        return distanciaKm;
    }

    public int getCantidadEstaciones() {
        return cantidadEstaciones;
    }
}
