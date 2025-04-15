package viaje_b;

public class Main {
    public static void main(String[] args) {
        Trayecto trayecto = new Trayecto(120, 6);
        TipoDeViaje tipoDiesel = new TipoDiesel();
        TipoDeViaje tipoElectrico = new TipoElectrico();
        TipoDeViaje tipoAltaVelocidad = new TipoAltaVelocidad();

        Viaje diesel = new Viaje(trayecto, 10, 500, tipoDiesel);
        Viaje electrico = new Viaje(trayecto, 8, 400, tipoElectrico);
        Viaje altaVelocidad = new Viaje(trayecto, 5, 300, tipoAltaVelocidad);

        System.out.println("Tiempo diesel: " + diesel.tiempoDeDemora() + " min");
        System.out.println("Tiempo eléctrico: " + electrico.tiempoDeDemora() + " min");
        System.out.println("Tiempo alta velocidad: " + altaVelocidad.tiempoDeDemora() + " min");
    }
}

