package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import importador.CSVExporter;
import importador.CSVParser;
import tabla.*;
import java.util.function.*;
/*
 ##################################
#### Prueba 2: TIPOS DE DATOS ####
##################################

Prueba 2.1: Para todos los DataFrames (DF 1 al 8) devolver info, head(x), tail(x) (x un numero distinto en cada DataFrame)

Prueba 2.2: Elminar 1 fila y 1 columnas completas del DF 1.

Prueba 2.3: Acceder a valores de filas y celdas de DFs que luego van a borrarse. (Posterior a borrarlas se va a acceder de nuevo y controlar que los valores sean distintos)

Prueba 2.4: Eliminar 2 filas no consecutivas del DF 2.

Prueba 2.5: Eliminar 2 columnas no consecutivas tomando la primera del DF 3.

Prueba 2.6: Eliminar 2 filas y 2 columnas del DF 4 no consecutivas, tomando la ultima fila y ultima columna.

Prueba 2.7: Acceder nuevamente a las posiciones borradasen las pruebas 2.4 a 2.6 (Deben ser distintos a los valores de la prueba 2.3)

Prueba 2.8: Insertar una columna nueva en la mitad en DF1. 

Prueba 2.9 : Insertar una columna al principio y una fila al final en DF2.

Prueba 2.10: Modificar valores NA de DF3, dejando un valor NA disponible. Acceder a los valores modificados y al valor NA dejado.

Prueba 2.11: Crear una nueva columna en DF4 al principio con todos valores NA y label a eleccion.

Prueba 2.12: Crear una fila con todos valores NA en DF 5 al final.

Prueba 2.13: De todos los dataframes editados devolver info, head(x), tail(x). Siendo el mismo x, compatible con el x de la prueba 2.1

Prueba 2.14: Eliminar los valores NA de DF4 y DF5.
 */

public class TestAccesoModificacion {
    public static void main(String[] args){
        CSVParser csvParser = new CSVParser();
        DataFrame df1 = new DataFrame();
        DataFrame df2 = new DataFrame();

        try{
            df1 = csvParser.toDataFrame(System.getProperty("user.dir").toString()+"/Trabajo Practico Final - Algo1/prueba1.csv");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        df1.head(2);
        df1.tail(2);

        System.out.println("\n La cantidad de columnas es: " + df1.contarColumnas());
        System.out.println("\n La cantidad de filas es: " + df1.contarFilas()+ "\n");
        df1.info();
        
        //Prueba 2.2: Elminar 1 fila y 1 columnas completas del DF 1.

        df1.removeRow(1);
        df1.removeRow(3);
         
        try{
            df1.removeRow(9);
        }catch(Exception e){
            System.out.println("\n"+ e.getMessage()+"\n");
        }

        df1.removeColumn("Edad");
        df1.head(5);

        //Acceder al valor de una celda
        System.out.println(df1.obtenerCelda(0,"Apellido"));

        try{
            df2 = csvParser.toDataFrame(System.getProperty("user.dir").toString()+"/Trabajo Practico Final - Algo1/prueba2.csv");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            List<Object> numeros = List.of(10, "20", 30);
            df1.addColumnFromList(numeros, "Numeros");
        }catch(Exception e){
            System.out.println("\n" + e.getMessage());
        }

        //Agrego una columna a partir de una lista

        List<Object> numeros = List.of(10, 20, 30);
        df1.addColumnFromList(numeros, "Numeros");

        df1.head(5);

        //Modificación del valor de una celda
        try{
            df1.setValue(4, "Numeros", "tuki");
        }catch(Exception e){
            System.out.println("\n" + e.getMessage()+"\n");
        }

        df1.setValue(4, "Numeros", 40);
        df1.head(5);

        //Imputación de valores faltantes en el segundo DataFrame
        System.out.println("Imputación de valores faltantes en df2 \n");
        df2.fillna("Apellido","Fernandez");
        df2.head(10);
        
        //Carga del DataFrame en un CSV
        CSVExporter exportador = new CSVExporter();
        exportador.exportDataFrame(System.getProperty("user.dir").toString()+"/Trabajo Practico Final - Algo1/dataframe_exportado.csv", df2);

    }
}
