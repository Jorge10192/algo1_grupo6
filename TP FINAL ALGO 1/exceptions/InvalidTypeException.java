package exceptions;

public class InvalidTypeException extends RuntimeException{
    public InvalidTypeException(){
        super("El tipo de la celda no coincide con el de la columna.");
    }
}
