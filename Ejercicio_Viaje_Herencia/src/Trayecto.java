public class Trayecto {
    // Atributos
    private float distanciaKm;
    private int cantidadEstaciones;

    //Constructor
    public Trayecto(float distanciaKm, int cantidadEstaciones){
        this.distanciaKm = distanciaKm;
        this.cantidadEstaciones = cantidadEstaciones;
    }
    //getters
    public float getDistanciaKm(){
        return distanciaKm;
    }
    public int getCantidadEstaciones(){
        return cantidadEstaciones;
    }

}
