package importador;

import tabla.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import exceptions.InvalidShape;

public class CSVParser {

    public DataFrame toDataFrame(String path, String sep, boolean has_headers){
        try{
            List<String> lineas = leerLineas(path);
            try{
                Object[][] celdas = parserLineas(lineas,sep);
                List<List<Object>> lista_celdas = convertirArrayALista(celdas);
                try{
                    if(has_headers==false){
                        return new DataFrame(lista_celdas, null,null);
                    }else{
                        List<Object> headers = lista_celdas.get(0);
                        lista_celdas.remove(0);
                        return new DataFrame(lista_celdas, headers,null);
                    }}
                catch(InvalidShape e){
                    System.err.println(e.getMessage());
                }
            }catch(CSVParserException e){
                System.err.println(e.getMessage());}

        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public static List<String> leerLineas(String filepath) throws IOException {

        /*Lee un archivo línea por línea y devuelve una lista con todas las líneas.

        Usa BufferedReader para una lectura eficiente.

        Devuelve una List<String> donde cada elemento es una línea del archivo. */

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String linea;
            List<String> lineas = new LinkedList<>();
            while ((linea = bufferedReader.readLine()) != null) {
                lineas.add(linea);
            }
            return lineas;
        } catch (IOException e) {
            throw e;
        }
    }

    public static Object[][] parserLineas(List<String> lineas, String sep) throws CSVParserException {
        /*Convierte una lista de líneas (como las obtenidas de un archivo CSV) en una matriz bidimensional de String.

        Divide cada línea por comas (,) usando split(",").

        Verifica que todas las líneas tengan la misma cantidad de campos. Si no, lanza una excepción personalizada CSVParserException. 
        
        ----------------------------------------------------------------------------------------------------------------------------------
        Modificaciones hechas: 
        - Agrego la opción de elegir el separador. Aunque no sé si es mejor definir al separador como atributo de clase y que se modifique con getter y setter
        - Agrego la posibilidad de que si hay un espacio vacío entre comas, su posición en el array sea completado por un null, para después ser manejado como N/A 
        - Parseo cada dato de String al tipo de dato más adecuado con ParseCellValue
        */
        
        if (sep == null){
            sep = ",";
        }

        int filas = lineas.size();
        Object[][] celdas = null;
        for(int i=0; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            String[] campos = linea.split(sep);
            if (celdas == null) {
                celdas = new Object[filas][campos.length];
            }
            if (celdas[0].length != campos.length) {
                throw new CSVParserException(i, celdas[0].length, campos.length);}
            for(int j=0; j < campos.length; j++) {
                celdas[i][j] = campos[j].isEmpty() ? null : parseCellValue(campos[j]);
            }
        }
        return celdas;
    }

        public static void mostrarLineas(List<String> lineas) {
        //Imprime todas las líneas en consola.
        for (String linea : lineas) {
            System.out.println(linea);
        }
    }
    
    public static String celdasToString(String[][] celdas, String sep) {

        /*Convierte una matriz de texto (String[][]) en una cadena formateada para visualización.

        Separa las celdas con el separador proporcionado (sep), por defecto " | ". */

        String salida = "";
        if (sep == null) {
            sep = " | ";
        }
        for (String[] fila : celdas) {
            for (String celda : fila) {
                salida += celda + sep;
            }
            salida += "\n";
        }
        return salida;

    }

    public static void exportCSV(String filepath, String[][] celdas) {
        String texto = celdasToString(celdas, ",");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            bufferedWriter.write(texto);
            // Si imprimiera por lineas (String[])
            // for(int i=0; i < lineas.length; i++) {
            //     bufferedWriter.write(lineas[i]);
            //     if (i < lineas.length - 1)
            //         bufferedWriter.newLine();
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<List<Object>> convertirArrayALista(Object[][] array) {
        List<List<Object>> lista2D = new ArrayList<>();
        for (Object[] fila : array) {
            List<Object> filaLista = new ArrayList<>();
            for (Object celda : fila) {
                filaLista.add(celda);
            }
            lista2D.add(filaLista);
        }
        return lista2D;
    }

    private static Object parseCellValue(String value) {
        if (value.matches("-?\\d+")) {
            return Integer.parseInt(value);
        } else if (value.matches("-?\\d*\\.\\d+")) {
            return Double.parseDouble(value);
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }

}
