import java.io.IOException;
import importador.ImportCSV;
import importador.ImportDataFrame;
import tabla.DataFrame;
import tabla.HeaderOption;

import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Ejemplo de DataFrame con CSV
        ImportDataFrame importador = new ImportCSV();
        try {
            DataFrame df = importador.importar("../prueba.csv", HeaderOption.WITH_HEADERS);
            System.out.println(df);
        } catch (IOException e) {
            System.out.println("Error al importar CSV: " + e.getMessage());
        }

        /*
        //Ejemplos de constructores de Listas<Listas>
        //Ejemplo 1
        List<List<Object>> datos1 = Arrays.asList(
                Arrays.asList("Nombre", "Edad", "Ciudad"),
                Arrays.asList("Ana", 25, "Córdoba"),
                Arrays.asList("Juan", 30, "Buenos Aires"),
                Arrays.asList("Lucía", 22, "Rosario")
        );

        DataFrame df1 = new DataFrame(datos1, HeaderOption.WITH_HEADERS);
        System.out.println("DataFrame 1 (con encabezado):");
        System.out.println(df1);


        //Ejemplo 2
        List<List<Object>> datos2 = Arrays.asList(
                Arrays.asList("Alumno", "Nota1", "Nota2", "Promedio"),
                Arrays.asList("Carlos", 7, 8, 7.5),
                Arrays.asList("María", 10, 9, 9.5),
                Arrays.asList("Pedro", 6, 5, 5.5)
        );

        DataFrame df2 = new DataFrame(datos2, HeaderOption.WITH_HEADERS);
        System.out.println("DataFrame 2 (con encabezado):");
        System.out.println(df2);

        //Ejemplo 3
        List<List<Object>> datos3 = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        DataFrame df3 = new DataFrame(datos3, HeaderOption.WITHOUT_HEADERS);
        System.out.println("DataFrame 3 (sin encabezado):");
        System.out.println(df3);

        //Ejemplo 4
        List<List<Object>> datos4 = Arrays.asList(
                Arrays.asList("Lunes", true, 15.5),
                Arrays.asList("Martes", false, 22.0),
                Arrays.asList("Miércoles", true, 18.3)
        );

        DataFrame df4 = new DataFrame(datos4, HeaderOption.WITHOUT_HEADERS);
        System.out.println("DataFrame 4 (sin encabezado):");
        System.out.println(df4);


        //Ejemplos de constructor matriz 2D
        //Ejemplo 1
        Object[][] datosEjemplo1 = {
                {"Nombre", "Edad", "Ciudad"},
                {"Ana", 25, "Córdoba"},
                {"Juan", 30, "Buenos Aires"}
        };

        DataFrame df_1 = new DataFrame(datosEjemplo1, HeaderOption.WITH_HEADERS);
        System.out.println("DataFrame 1 (con encabezado):");
        System.out.println(df_1);

        //Ejemplo 2
        Object[][] datosEjemplo2 = {
                {"Ana", 25, "Córdoba"},
                {"Juan", 30, "Buenos Aires"}
        };

        DataFrame df_2 = new DataFrame(datosEjemplo2, HeaderOption.WITHOUT_HEADERS);
        System.out.println("DataFrame 2 (sin encabezado):");
        System.out.println(df_2);

        //Ejemplo 3
        Object[][] productos = {
                {"Producto", "Precio", "Stock"},
                {"Notebook", 1200.5, 10},
                {"Teclado", 45.0, 50},
                {"Mouse", 25.0, 100}
        };

        DataFrame df_3 = new DataFrame(productos, HeaderOption.WITH_HEADERS);
        System.out.println("DataFrame 3 (con encabezado):");
        System.out.println(df_3);

        //Ejemplo 4
        Object[][] horarios = {
                {"Pedro", "08:00", "16:00"},
                {"Lucía", "09:00", "17:00"},
                {"Carlos", "10:00", "18:00"}
        };

        DataFrame df_4 = new DataFrame(horarios, HeaderOption.WITHOUT_HEADERS);
        System.out.println("DataFrame 4 (sin encabezado):");
        System.out.println(df_4);
        */

    }
}