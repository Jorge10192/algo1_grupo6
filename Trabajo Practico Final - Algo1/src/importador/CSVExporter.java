package importador;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import exceptions.EncodingException;
import tabla.*;

public class CSVExporter {
    // Atributos
    private String sep = ",";
    private String encoding = "UTF-8";
    private boolean hasHeaders = true;

    // Constructores
    public CSVExporter() {}

    public CSVExporter(String separator, String encoding, boolean hasHeaders) {
        this.sep = separator;
        setEncoding(encoding); // validación incluida
        this.hasHeaders = hasHeaders;
    }

    // Getters
    public String getSeparador() { return sep; }
    public String getEncoding() { return encoding; }
    public boolean hasHeaders() { return hasHeaders; }

    // Setters
    public void setSeparador(String sep) {
        this.sep = sep;
    }

    public void setEncoding(String encoding) {
        if (!EncodingUtils.encodings.contains(encoding)) {
            throw new EncodingException(encoding);
        }
        this.encoding = encoding;
    }

    public void setHasHeaders(boolean hasHeaders) {
        this.hasHeaders = hasHeaders;
    }

    // Exportar CSV
    public void exportCSV(String filepath, String[][] celdas) {
        String texto = celdasToString(celdas);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filepath), encoding))) {
            writer.write(texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Exportar dataFrame
    public void exportDataFrame(String filepath, DataFrame df){

        List<Column> columns = df.getColumns();
        List<Row> rows = df.getRows();

        if(!hasHeaders){

            String [][] celdas = new String [df.contarFilas()][df.contarColumnas()];

            for (int i=0; i<rows.size(); i++){
                for (int j=0; j<columns.size(); j++){
                    celdas[i][j] = columns.get(j).getCells().get(i).toString();
                }
            }
            exportCSV(filepath, celdas);
        }else{

            String [][] celdas = new String [df.contarFilas()+1][df.contarColumnas()];
            int j=0;
            for(Column c:columns){
                celdas[0][j] = c.getLabel().toString();
                j++;
            }
            for (int i=0; i<rows.size(); i++){
                for (int k=0; k<columns.size(); k++){
                    celdas[i+1][k] = columns.get(k).getCells().get(i).toString();
                }
            }
            exportCSV(filepath, celdas);
        }
        
    }

    // Convertir matriz a texto CSV
    public String celdasToString(String[][] celdas) {
        StringBuilder salida = new StringBuilder();

        for (int i = 0; i < celdas.length; i++) {
            String[] fila = celdas[i];
            for (int j = 0; j < fila.length; j++) {
                salida.append(fila[j]);
                if (j < fila.length - 1) {
                    salida.append(sep);
                }
            }
            salida.append("\n");
        }
        return salida.toString();
    }
}

