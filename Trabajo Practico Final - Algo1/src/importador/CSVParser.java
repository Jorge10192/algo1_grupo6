package importador;

import tabla.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import exceptions.*;

public class CSVParser {
    
    //Atributos
    private String sep = ",";
    private String encoding = "UTF-8";
    private boolean hasHeaders = true;
    
    //Constructores
    public CSVParser(){}

    public CSVParser(String separator, String encoding, boolean hasHeaders) {
        this.sep = separator;
        this.encoding = encoding;
        this.hasHeaders = hasHeaders;
    }

    //Getters
    public String getSeparador(){
        return sep;
    }
    public String getEncoding(){
        return encoding;
    }
    public boolean hasHeaders(){
        return hasHeaders;
    }

    //Setters
    public void setSeparador(String sep){
        this.sep = sep;
    }
    public void setEncoding(String encoding){
        if(!EncodingUtils.encodings.contains(encoding)){
            throw new EncodingException(encoding);
        }
        this.encoding = encoding;
    }
    public void setHasHeaders(boolean hasHeaders){
        this.hasHeaders = hasHeaders;
    }

    //Comportamiento

    public DataFrame toDataFrame(String path) throws IOException, CSVParserException, InvalidShape{

        File file = new File(path);
            if (!file.exists() || !file.canRead()) {
                throw new IOException("El archivo no existe o no se puede leer: " + path);
            }

        List<String> lineas = leerLineas(path, encoding);

        Object[][] celdas = parserLineas(lineas,sep);
        List<List<Object>> lista_celdas = convertirArrayALista(celdas);
        
        if(hasHeaders==false){
            return new DataFrame(lista_celdas, null,null);

        }else{
            List<Object> headers = lista_celdas.get(0);
            lista_celdas.remove(0);
            return new DataFrame(lista_celdas, headers,null);
        }

    }
    
    public static List<String> leerLineas(String filepath, String encoding) throws IOException {

        /*Lee un archivo línea por línea y devuelve una lista con todas las líneas.

        Usa BufferedReader para una lectura eficiente.

        Devuelve una List<String> donde cada elemento es una línea del archivo. */

        if(!EncodingUtils.encodings.contains(encoding)){
            throw new EncodingException(encoding);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), Charset.forName(encoding)))) {
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
            String[] campos = linea.split(sep,-1);
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
    
    public static String celdasToString(Object[][] celdas, String sep) {

        /*Convierte una matriz de texto (String[][]) en una cadena formateada para visualización.

        Separa las celdas con el separador proporcionado (sep), por defecto " | ". */

        String salida = "";
        if (sep == null) {
            sep = " | ";
        }
        for (Object[] fila : celdas) {
            for (Object celda : fila) {
                salida += celda.toString() + sep;
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
