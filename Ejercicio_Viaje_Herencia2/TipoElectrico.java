package viaje_b;

public class TipoElectrico extends TipoDeViaje {
    @Override
    public double tiempoDeDemora(Trayecto trayecto, int capacidadPasajeros) {
        return (trayecto.getDistanciaKm() * trayecto.getCantidadEstaciones()) / 2.0;
    }
}
