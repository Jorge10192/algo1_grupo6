package importador;

import tabla.DataFrame;
import tabla.HeaderOption;
import tabla.IndexOption;

import java.io.*;
import java.util.*;

public class ImportCSV implements ImportDataFrame {

    private IndexOption indexOption;

    public ImportCSV(IndexOption indexOption) {
        this.indexOption = indexOption;
    }

    @Override
    public DataFrame importar(String rutaArchivo, HeaderOption headerOption) throws IOException {
        List<List<Object>> datos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                List<Object> fila = new ArrayList<>();
                for (String valor : partes) {
                    fila.add(valor.trim());
                }
                datos.add(fila);
            }
        }

        return new DataFrame(datos, headerOption, indexOption);
    }
}