package viaje_b;

public class Viaje {
    private Trayecto trayecto;
    private int cantidadVagones;
    private int pasajerosMax;
    private TipoDeViaje tipo;

    public Viaje(Trayecto trayecto, int cantidadVagones, int pasajerosMax, TipoDeViaje tipo) {
        this.trayecto = trayecto;
        this.cantidadVagones = cantidadVagones;
        this.pasajerosMax = pasajerosMax;
        this.tipo = tipo;
    }

    public double tiempoDeDemora() {
        return tipo.tiempoDeDemora(trayecto, pasajerosMax);
    }
}
