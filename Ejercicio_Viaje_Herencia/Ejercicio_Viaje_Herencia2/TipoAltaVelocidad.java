package viaje_b;

public class TipoAltaVelocidad extends TipoDeViaje {
    @Override
    public double tiempoDeDemora(Trayecto trayecto, int capacidadPasajeros) {
        return trayecto.getDistanciaKm() / 10.0;
    }
}
