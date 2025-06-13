package tests;
import java.util.ArrayList;
import java.util.List;

import importador.*;
import tabla.DataFrame;

/* 
##############################################
#### Prueba 1: Construccion del DataFrame ####
##############################################

Prueba 1.1: DF1 --> Armar un DataFrame desde CSV con encabezado
Prueba 1.2: DF2 --> Armar un DataFrame desde CSV sin encabezado

Prueba 1.4: DF4 --> Armar un DataFrame desde una Lista de Lista con Labels de columnas y filas.
Prueba 1.5: DF5 --> Armar un DataFrame desde una Lista de Lista sin Labels de columnas y sin Lables de filas.

Prueba 1.6: DF6 --> Armar un DataFrame desde una matriz 2D con Labels de Filas, sin Labels de Columnas.

Prueba 1.7: DF7 --> Armar DataFrame con 1 sola columna (1 lista sola)

Prueba 1.8: DF8 --> Armar un DataFrame a partir de otro.
*/

public class TestConstructores {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();
        //Prueba 1.1
        try{
            DataFrame df1 = csvParser.toDataFrame("C:/Users/usuario/Downloads/prueba2.csv");
            df1.head(5);
            df1.info();
        }catch(Exception e){
            System.err.println(("Error al importar el CSV: " + e.getMessage()));
        }

        //Prueba 1.2
        csvParser.setHasHeaders(false);
        System.out.println("\n Prueba 1.2: DataFrame cargado sin headers \n");
        try{
            DataFrame df2 = csvParser.toDataFrame("C:/Users/usuario/Downloads/prueba3.csv");
            df2.head(5);
            df2.info();
        }catch(Exception e){
            System.err.println(("Error al importar el CSV: " + e.getMessage()));
        }

        //Prueba 1.4: DF4 --> Armar un DataFrame desde una Lista de Lista con Labels de columnas y filas.

        
        List<List<Object>> lista = new ArrayList<>();

        
        List<Object> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        List<Object> list2 = new ArrayList<>();
        list2.add(4);
        list2.add(5);
        list2.add(6);

        List<Object> list3 = new ArrayList<>();
        list3.add(null);
        list3.add(8);
        list3.add(9);

        lista.add(list1);
        lista.add(list2);
        lista.add(list3);

        //Lista de labels
        List<Object> labels = new ArrayList<>();
        labels.add("A");
        labels.add("B");
        labels.add("C");
        System.out.println("\n Prueba 1.4: DataFrame desde una Lista de Lista con Labels de columnas y filas. \n");
        DataFrame df3 = new DataFrame(lista, labels, labels);
        df3.head(5);

        //Prueba 1.5: DF5 --> Armar un DataFrame desde una Lista de Lista sin Labels de columnas y sin Lables de filas.
        System.out.println("\n Prueba 1.5: DataFrame desde una Lista de Lista sin Labels de columnas o filas. \n");
        DataFrame df4 = new DataFrame(lista, null, null);
        df4.head(5);

        //Prueba 1.6: DF6 --> Armar un DataFrame desde una matriz 2D con Labels de Filas, sin Labels de Columnas.
        Object[][] array = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("\n Prueba 1.6: DataFrame desde una matriz 2D \n");
        DataFrame df5 = new DataFrame(array,null, null);
        df5.head(5);

        //Pongo a prueba la condición de que una columna sea del mismo tipo
        Object[][] array2 = {
            {1, 2, 3},
            {4, "Hola", 6},
            {7, 8, 9}
        };
        try{DataFrame df6 = new DataFrame(array2,null, null);
            df6.head(5);}
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        //Prueba 1.7: DF7 --> Armar DataFrame con 1 sola columna (1 lista sola)
        System.out.println("\n Prueba 1.7: DataFrame desde una lista \n");
        List<Object> numeros = List.of(10, 20, 30, 40, 50, 60, 70, 80);
        DataFrame df7 = new DataFrame(numeros, "Numeros", null);
        df7.head(5);

        //Prueba 1.8: DF8 --> Armar un DataFrame a partir de otro.
        System.out.println("\n Prueba 1.8: DataFrame a partir de otro \n");
        DataFrame df8 = new DataFrame(df3);
        df8.head(5);
    }
}
