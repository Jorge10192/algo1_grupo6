//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import tabla.*;
import importador.*;
import exceptions.InvalidShape;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
/*
La clase Logger permite registrar depuraciones y excepciones durante la ejecución, si bien
no esperaba utilizar esta clase, el compilador lo arrojo como alerta de buena practica.
* */

public class Main {
    //Clase de java que permite registrar mensaje durante la ejecucion de un programa.
    //private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws InvalidShape {

        //Ejemplo de creacion de un DataFrame interno en JAVA
        List<List<Object>> lista = new ArrayList<>();

        // Creating and adding lists to the main list
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

        // Adding individual lists to the main list
        lista.add(list1);
        lista.add(list2);
        lista.add(list3);

        //Lista de labels
        List<Object> labels = new ArrayList<>();
        labels.add("A");
        labels.add("B");
        labels.add("C");

        DataFrame df1 = new DataFrame(lista, labels, labels);
        df1.head(3);
        
        CSVParser csvParser = new CSVParser();
        try{
            DataFrame df2 = csvParser.toDataFrame("/home/tareas/algo1_grupo6/Trabajo Practico Final - Algo1/prueba2.csv");
            df2.head(5);
            System.out.println("La cantidad de filas es: " + df2.contarColumnas());
            System.out.println("La cantidad de filas es: " + df2.contarFilas());
            df2.info();
            System.out.println(df2.obtenerFila(new Label(3)));
        }catch(Exception e){
            System.err.println(("Error al importar el CSV: " + e.getMessage()));;
        }
        try{
            DataFrame df3 = csvParser.toDataFrame("C:/Users/usuario/Downloads/prueba3.csv");
            df3.head(5);
        }catch(Exception e){
            System.err.println(("Error al importar el CSV: " + e.getMessage()));;
        }



        /*Test unitarios recomendados:

            CSV con y sin headers.

            Valores numéricos, booleanos y string.

            Campos vacíos o nulos.

            Archivos con separadores personalizados.

            Codificaciones no válidas.

            Líneas con distinta cantidad de columnas 
        */
    }
}
