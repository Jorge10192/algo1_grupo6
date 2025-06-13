package Tabla;

import Excepciones.InvalidShape;
import Excepciones.InvalidTypeException;
// o import Excepciones.*;

import java.util.ArrayList;
import java.util.List;

public class DataFrame {

    // ---- 1. Atributos ----

    private List<Column> columns;
    private List<Row> rows;
    //private DataFrameHandler handler;

    // ---- 2. Constructores ----

    //Constructor 2.1: Por defecto
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        //this.handler = new DataFrameHandler(this);
    }

    // Constructor 2.2.1: Lista de listas con columnas y filas (Cuatro versiones de un mismo constructor sobrecargado)
    /**
     * Constructor de la tabla a partir de una lista de listas que representa los datos,
     * crudos armados en filas y dos listas de etiquetas (Labels) para las columnas y las filas.
     *
     * @param data lista de listas que contiene las filas de la tabla (sin etiquetas)
     * @param columnLabels lista de etiquetas para las columnas (puede ser null)
     * @param rowLabels lista de etiquetas para las filas (puede ser null)
     * @throws InvalidShape si la forma de los datos no es correcta
     * @throws IllegalArgumentException si hay argumentos inválidos
     * @throws InvalidTypeException si algún tipo de dato no es válido
     */
    public DataFrame(List<? extends List<?>> data, List<?> columnLabels, List<?> rowLabels)
            throws InvalidShape, IllegalArgumentException, InvalidTypeException {
        this();

        List<Label> labelsC = adaptarLabels(columnLabels);
        List<Label> labelsR = adaptarLabels(rowLabels);

        generarDataFrame(data, labelsC, labelsR);
    }

    // Constructor 2.2.2: Lista de listas con etiquetas solo para columnas
    public DataFrame(List<? extends List<?>> data, List<?> columnLabels)
            throws InvalidShape, IllegalArgumentException, InvalidTypeException {
        this(data, columnLabels, null);
    }

    // Constructor 2.2.3: Lista de listas con etiquetas solo para filas
    public DataFrame(List<? extends List<?>> data, List<?> rowLabels, boolean isRowLabel)
            throws InvalidShape, IllegalArgumentException, InvalidTypeException {
        this(data, null, rowLabels);
    }

    //Constructor 2.2.4: Lista de listas sin etiquetas
    public DataFrame(List<? extends List<?>> data)
            throws InvalidShape, IllegalArgumentException, InvalidTypeException {
        this(data, null, null);
    }




    //---- Metodos Auxiliares para constructores ----

    //Metodo aux. 1: Convertir Listas de Datos a Listas de Labels
    private List<Label> adaptarLabels(List<?> labels) throws IllegalArgumentException{

        List<Label> aux = new ArrayList<>();

        if (labels == null || labels.isEmpty()) {
            return aux; // Devuelve lista vacía
        }

        for (Object l:labels){
            Label label = new Label<>(l);
            aux.add(label);
        }
        return aux;
    }

    //Metodo aux. 2: Metodo auxiliar constructor del DataFrame (Generador de Dataframe)
    /**
     * Metodo interno que construye la estructura interna del DataFrame a partir de una lista de filas (datos crudos)
     * y utiliza, etiquetas para columnas y filas.
     * Genera etiquetas de forma automatica si se proporcionan listas de etiquetas vacias.
     * Valida que la estructura de los datos ingresados sea consistente en tipos.
     *
     * @param rows lista de listas que representan las filas del DataFrame
     * @param columnLabels etiquetas para las columnas; si es null o está vacía, se generan automáticamente
     * @param rowLabels etiquetas para las filas; si es null o está vacía, se generan automáticamente
     * @throws InvalidShape si las filas no tienen tamaños consistentes o si no coinciden con las etiquetas
     */
    private void generarDataFrame(List<? extends List<?>> rows, List<Label> columnLabels, List<Label> rowLabels) throws InvalidShape {
        //Verifica que todas las listas que conforman las filas (rows) tengan el mismo tamaño
        validateRowSize(rows);

        //Verifica que la cantidad de celdas de cada fila, coincida con la cantidad de etiquetas de las columna.
        if(columnLabels!=null){
            validateRowShape(rows, columnLabels);
        }

        //Generar Labels de columnas(Los crea si la lista de Labels es vacia)
        if(columnLabels == null || columnLabels.isEmpty()){
            columnLabels = generateColumnLabels(rows);
        }

        //Generar Labels de filas (Los crea si la lista de Labels es vacia)
        if(rowLabels == null || rowLabels.isEmpty()){
            rowLabels = generateRowLabels(rows);
        }

        //Completar filas y columnas del DataFrame
        fillColumns(rows, columnLabels);
        fillRows(rowLabels);
    }

    //Metodo aux. 2.1: Validacion de tamanio de distintas filas.
    private void validateRowSize(List<? extends List<?>> rows) throws InvalidShape{
        int ref =rows.get(0).size();
        for (int i=1;i<rows.size();i++ ){
            if (rows.get(i).size()!=ref){

                throw new InvalidShape(); //Si las filas tienen distinto tamaño se arroja una excepción
            }
        }
    }

    //Metodo aux 2.2: Validacion de tamaño de filas con cant de etiquetas de columnas (celdas)
    private void validateRowShape(List<? extends List<?>> rows, List<Label> columnLabels)throws InvalidShape{
        for (List<?> row : rows){
            if(row.size()!=columnLabels.size()){
                throw new InvalidShape();
            }
        }
    }

    //Metodo aux 2.3: Generar Labels de columnas automatico como numeros enteros. (Empezando desde el 0)
    private List<Label> generateColumnLabels(List<? extends List<?>> rows){//argumento puede ser de tipo int: expectedSize

        int nCols = rows.get(0).size();
        List<Label> labels = new ArrayList<>();
        //Crea los labels
        for(int i=0; i<nCols; i++){
            labels.add(new Label(i));
        }

        return labels;
    }

    //Metodo aux 2.4: Generar Labels para filas.
    private List<Label> generateRowLabels(List<? extends List<?>> rows){ //argumento puede ser de tipo int
        List<Label> labels = new ArrayList<>();
        for(int i=0; i<rows.size(); i++){
            labels.add(new Label(i));
        }
        return labels;
    }

    //Metodo aux 2.5: Generar las columnas como objetos y armar la Lista<Columna> del DataFrame
    private void fillColumns(List<? extends List<?>> rows, List<Label> columnLabels) throws InvalidTypeException {
        for (int i = 0; i < columnLabels.size(); i++) {
            Label label = columnLabels.get(i);
            Column column = new Column(label);

            for (List<?> row : rows) {
                Object value = row.get(i);
                column.addCellFromObject(value); // Usa la lógica ya encapsulada
            }

            columns.add(column);
        }
    }

    //Metodo 2.6: Generar las filas como objetvos y armar la lista de filas del Dataframe.
    private void fillRows(List<Label> rowLabels){
        for (int i = 0; i < rowLabels.size(); i++) {
            this.rows.add(new Row(rowLabels.get(i),i));
        }
    }



}
