public abstract class Viaje {
    //Atributos
    protected int cantidadVagones;
    protected int pasajerosMax;
    protected Trayecto trayecto;

    //constructor
    public Viaje(int cantidadVagones, int pasajerosMax, Trayecto trayecto){
        this.cantidadVagones = cantidadVagones;
        this.pasajerosMax = pasajerosMax;
        this.trayecto = trayecto;

    }

    //Metodos
    public abstract double tiempoDeDemora();

}
