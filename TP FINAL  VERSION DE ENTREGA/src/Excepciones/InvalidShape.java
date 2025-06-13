package Excepciones;

public class InvalidShape extends RuntimeException {
    public InvalidShape(String message) {
        super(message);
    }
    public InvalidShape() {
        super("El numero de celdas no corresponde al numero de columnas.");
    }
}
