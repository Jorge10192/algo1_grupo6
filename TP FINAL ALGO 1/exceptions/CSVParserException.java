package exceptions;

public class CSVParserException extends Exception {
    public CSVParserException(int linea, int esperado, int actual) {
        super("Error en parseo de CSV en linea " + linea +
        ". Valor de columnas esperado: " + esperado + ", valor de columnas obtenido: " + actual);
    }
}
