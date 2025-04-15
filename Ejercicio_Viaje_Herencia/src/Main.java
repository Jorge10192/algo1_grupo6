//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main (String[] args){
        Trayecto trayecto1 = new Trayecto(100, 6);
        Viaje diesel = new ViajeDiesel(10, 400, trayecto1);
        Viaje electrico = new ViajeElectrico(10, 400,trayecto1);
        Viaje altaVelocidad = new ViajeAltaVelocidad(10,400, trayecto1);

        System.out.println("Demora de viaje Diesel: " + diesel.tiempoDeDemora());
        System.out.println("Demora de viaje electrico: " + electrico.tiempoDeDemora());
        System.out.println("Demora de viaje de alta velocidad: " + altaVelocidad.tiempoDeDemora());
    }
}