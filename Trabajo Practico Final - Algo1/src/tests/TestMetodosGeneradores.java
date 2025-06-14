package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.InvalidShape;
import importador.CSVExporter;
import importador.CSVParser;
import tabla.*;
import java.util.function.*;

public class TestMetodosGeneradores {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();
        try{
            DataFrame df1 = csvParser.toDataFrame(System.getProperty("user.dir").toString()+"/Trabajo Practico Final - Algo1/prueba1.csv");
            DataFrame df2 = csvParser.toDataFrame(System.getProperty("user.dir").toString()+"/Trabajo Practico Final - Algo1/prueba2.csv");
            
            //Slicing en DataFrame 1
            System.out.println("\n Slicing \n");
            List<Object> lista_labels = new ArrayList<>();
            lista_labels.add("Nombre");
            lista_labels.add("Apellido");

            df2.slice(lista_labels,null);

            //Concatenación de DataFrames
            System.out.println("\n Concatencación \n");
            DataFrame df3 = df1.concatenar(df2);
            df3.head(10);
            df3.info();

            df3.setValue(2, "Edad", 25);

            df3.head(10);

            //Filtrado
            System.out.println("\n Filtrado \n");
            Map<Object, Predicate<Object>> condiciones = new HashMap<>();
            condiciones.put("Edad", v -> ((Integer)v) > 24);

            DataFrame dfFiltrado = df3.filter(condiciones);
            dfFiltrado.head(5);
            
            //Ordenamiento
            System.out.println("\n Ordenamiento \n");
            List<Object> lista_labels_2 = new ArrayList<>();
            lista_labels_2.add("Nombre");
            DataFrame dfOrdenado = df3.sortBy(lista_labels_2, false);

            //Copio el df ordenado
            DataFrame dfCopia = dfOrdenado.copy();
            System.out.println("\n Copia \n");
            dfCopia.head(10);

            //Sampleo el 60% de df3
            System.out.println("\n Sampleo \n");
            df3.sample(60).head(10);

            //CSVExporter exporter = new CSVExporter();
            //exporter.exportDataFrame(System.getProperty("user.dir").toString()+"/Trabajo Practico Final - Algo1/prueba4.csv", df3);

        }catch(Exception e){
            System.err.println(("Error al importar el CSV: " + e.getMessage()));;
        }

    }
}
