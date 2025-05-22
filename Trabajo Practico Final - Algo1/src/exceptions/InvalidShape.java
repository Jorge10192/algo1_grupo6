package exceptions;

public class InvalidShape extends Exception {
    public InvalidShape(String message) {
        super(message);
    }
    public InvalidShape() {
        super("El numero de celdas no corresponde al numero de columnas.");
    }
}