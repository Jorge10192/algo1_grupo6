package viaje_b;

public class TipoDiesel extends TipoDeViaje {
    @Override
    public double tiempoDeDemora(Trayecto trayecto, int capacidadPasajeros) {
        int d = trayecto.getDistanciaKm();
        int e = trayecto.getCantidadEstaciones();
        return (d * e) / 2.0 + (e + capacidadPasajeros) / 10.0;
    }
}
