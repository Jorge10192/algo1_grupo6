package importador;

//Importo paquete tabla
import tabla.DataFrame;
import tabla.Column;
import tabla.Row;
import tabla.Cell;

//Por si falla lectura del archivo
import java.io.IOException;

//Lectura de archivo CSV
import java.io.BufferedReader;
import java.io.FileReader;

//Dependencias de Listas y ArrayList
import java.util.List;
import java.util.ArrayList;

public class ImportCSV implements ImportDataFrame {

    @Override
    public DataFrame importar(String rutaArchivo) throws IOException {
        DataFrame df = new DataFrame();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = br.readLine();
            if (linea == null) return df;  // archivo vacío

            // Leer columnas (encabezado)
            String[] encabezados = linea.split(",");
            for (String nombreColumna : encabezados) {
                //Asumimos tipo String para todas las columnas
                df.addColumn(new Column(nombreColumna.trim(), String.class));
            }

            //Leer filas
            int id = 1;  // Contador para el ID de fila
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                List<Cell> celdas = new ArrayList<>();
                for (String valor : valores) {
                    //Orden del constructor Cell, (valor, nombre)
                    celdas.add(new Cell(valor.trim(), String.class));
                }
                df.addRow(new Row(id++, celdas));
            }


        }

        return df;
    }

}

