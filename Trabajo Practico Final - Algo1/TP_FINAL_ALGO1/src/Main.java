import java.io.IOException;
import importador.ImportCSV;
import importador.ImportDataFrame;
import tabla.DataFrame;

public class Main {
    public static void main(String[] args) {

        ImportDataFrame importador = new ImportCSV();
        try {
            DataFrame df = importador.importar("../prueba.csv");
            System.out.println(df);
        } catch (IOException e) {
            System.out.println("Error al importar CSV: " + e.getMessage());
        }

    }
}