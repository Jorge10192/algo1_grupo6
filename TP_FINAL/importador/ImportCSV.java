package importador;

import tabla.DataFrame;
import exceptions.CSVParserException;
import exceptions.InvalidShape;

import java.io.*;
import java.util.*;

public class ImportCSV implements ImportDataFrame {
    //Si no tiene atributos considerar usarlo como interface.
    //Constructor: DataFrame a partir de una ruta de un CSV
    /**
     * Constructor usando ruta de un CSV y
     * y declarando si tiene o no encabezados (columLabels) incorporados
     *
     * @param rutaArchivo ruta del archivo CSV
     * @param separator String que identifica el caracter tabulador de celdas del CSV.
     * @param encoding String que identifica el tipo de codificacion del CSV (Ej. UTF-8 por defecto).
     * @param hasHeader booleano que identifica si el CSV tiene encabezados.
     *
     * @throws IOException si falla al leer las lineas del CSV.
     * @throws CSVParserException si falla al parsear las lineas del CSV ya leidas correctamente.
     */
    @Override
    public DataFrame importar(String rutaArchivo, String separator, String encoding, boolean hasHeader) throws IOException, CSVParserException {
        List<String> lineas = leerLineas(rutaArchivo);
        String[][] celdas = parserLineas(lineas, separator, encoding);
        List<List<Object>> datos = new ArrayList<>();
        List<Object> columnLabels = null;
        //Armo matriz de 2 Dimensiones (Filas-i; completo Columnas-j por fila i-esima)
        for (int i = 0; i < celdas.length; i++) {
            List<Object> fila = new ArrayList<>();

            for (int j = 0; j < celdas[i].length; j++) {
                fila.add(parseValue(celdas[i][j].trim()));
            }
            //Identifico si tiene o no encabezado en primera fila
            if (i == 0 && hasHeader) {
                columnLabels = fila;
            } else {
                datos.add(fila);
            }
        }

        //Arrojo excepcion de construccion
        try {
            return new DataFrame(datos, columnLabels, null);
        } catch (InvalidShape e) {
            throw new IOException("Error construyendo DataFrame: " + e.getMessage());
        }
    }

    //Metodo aux. 1: Armado de lista de Strings al leer el CSV (como filas unicas)
    private List<String> leerLineas(String path) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String linea;
            //Termina la lectura cuando la ultima linea es vacia.
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }

    //Metodo aux. 2: Separador de lineas del CSV (filas de Strings) en celdas individuales.
    private String[][] parserLineas(List<String> lineas, String separator, String encoding) throws CSVParserException {
        int filas = lineas.size();
        String[][] celdas = null;

        for (int i = 0; i < lineas.size(); i++) {
            String[] campos = lineas.get(i).split(separator);
            if (celdas == null) {
                celdas = new String[filas][campos.length];
            }
            if (campos.length != celdas[0].length) {
                throw new CSVParserException(i + 1, celdas[0].length, campos.length);
            }
            celdas[i] = campos;
        }

        return celdas;
    }

    //Identificador/Parseo de valores de cada celda que es un String.
    private Object parseValue(String valor) {
        if (valor.isEmpty() || valor.equalsIgnoreCase("NA")) return DataFrame.NA;

        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException ignored) {}

        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException ignored) {}

        if (valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(valor);
        }

        return valor;
    }
}
