package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.InvalidShape;
import importador.CSVParser;
import tabla.*;

public class test {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser();
        try{
            DataFrame df1 = csvParser.toDataFrame("C:/Users/usuario/Downloads/prueba1.csv");
            DataFrame df2 = csvParser.toDataFrame("C:/Users/usuario/Downloads/prueba2.csv");

            DataFrame df3 = df1.concatenar(df2);

            df3.head(10);

            
        }catch(Exception e){
            System.err.println(("Error al importar el CSV: " + e.getMessage()));;
        }

    }
}
