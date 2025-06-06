package tabla;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import exceptions.InvalidShape;

public class DataFrame {

    private List<Column> columns;
    private List<Row> rows;
    public static final Object NA = new Object() {
        @Override
        public String toString() {
            return "N/A";
        }
    };

    //Constructores
    //Constructor 1: por defecto
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    //Constructor 2: Array 2D
    /**
     * Constructor de la tabla a partir de una matriz bidimensional
     * y dos listas de etiquetas: una para las filas y otra para las columnas.
     *
     * @param array2D matriz de datos sin incluir etiquetas de filas ni de columnas
     * @param columnLabels lista de etiquetas para las columnas
     * @param rowLabels lista de etiquetas para las filas
     * @throws InvalidShape si la cantidad de datos no coincide con las etiquetas
     */
    public DataFrame(Object[][] array2D, List<Object> columnLabels, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException {
        this();
        List<List<Object>> data = new ArrayList<>();
        for (Object[] row : array2D) {
            data.add(Arrays.asList(row));
        }

        List<Label> labelsC = adaptarLabels(columnLabels);
        List<Label> labelsR = adaptarLabels(rowLabels);

        generarDataFrame(data, labelsC, labelsR);
    }

    //Constructor 3: Lista de listas
    /**
     * Constructor de la tabla a partir de una lista de listas que representa los datos,
     * y dos listas de etiquetas para las columnas y las filas.
     *
     * @param data lista de listas que contiene las filas de la tabla (sin etiquetas)
     * @param columnLabels lista de etiquetas para las columnas
     * @param rowLabels lista de etiquetas para las filas
     * @throws InvalidShape si la cantidad de datos no coincide con las etiquetas
     */
    public DataFrame(List<? extends List<?>> data, List<Object> columnLabels, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException {
        this();

        List<Label> labelsC = adaptarLabels(columnLabels);
        List<Label> labelsR = adaptarLabels(rowLabels);

        generarDataFrame(data, labelsC, labelsR);
    }

    //Constructor 4: Lista Columna (1 sola lista)
    /**
     * Constructor de la tabla a partir de una única columna de datos, una etiqueta de columna
     * y una lista de etiquetas de fila.
     *
     * @param columnData lista que contiene los valores de la única columna
     * @param columnLabel etiqueta de la columna
     * @param rowLabels lista de etiquetas para las filas
     * @throws InvalidShape si la cantidad de valores no coincide con la cantidad de etiquetas de fila
     */
    public DataFrame(List<Object> columnData, Object columnLabel, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException {
        this();
        List<List<Object>> data = new ArrayList<>();
        for (Object value : columnData) {
            List<Object> row = new ArrayList<>();
            row.add(value);
            data.add(row);
        }

        List<Label> labelsC = adaptarLabels(List.of(columnLabel));
        List<Label> labelsR = adaptarLabels(rowLabels);

        generarDataFrame(data, labelsC, labelsR);
    }


    //Metodos auxiliares para los constructores

    //Metodo aux. 1: Metodo auxiliar constructor del DataFrame (Generador de Dataframe)
    /**
     * Metodo interno que construye la estructura del DataFrame a partir de una lista de filas
     * y etiquetas opcionales para columnas y filas. Valida la forma de los datos y genera
     * etiquetas si no se proporcionan.
     *
     * @param rows lista de listas que representan las filas del DataFrame
     * @param columnLabels etiquetas para las columnas; si es null o está vacía, se generan automáticamente
     * @param rowLabels etiquetas para las filas; si es null o está vacía, se generan automáticamente
     * @throws InvalidShape si las filas no tienen tamaños consistentes o si no coinciden con las etiquetas
     */
    private void generarDataFrame(List<? extends List<?>> rows, List<Label> columnLabels, List<Label> rowLabels) throws InvalidShape {
        //Verifica que todas las listas que conforman las filas (rows) tengan el mismo tamaño
        validateRowSize(rows);

        //Para la lista de filas, se verifica que el largo de cada fila coincida con la cantidad de headers de columnas
        if(columnLabels!=null){
            validateRowShape(rows, columnLabels);
        }

        //Manejar labels de columnas: generar nuevos labels si la lista es vacia.
        if(columnLabels == null || columnLabels.isEmpty()){
            columnLabels = generateColumnLabels(rows);
        }

        //Manejar labels de filas: generar nuevos labels si la lista es vacia.
        if(rowLabels == null || rowLabels.isEmpty()){
            rowLabels = generateRowLabels(rows);
        }

        //Completar filas y columnas del DataFrame
        fillColumns(rows, columnLabels);
        fillRows(rowLabels);
    }

    //Metodos para completar generarDataFrame
    //Metodo aux. 1.1: ValidateRowSize
    /**
     *
     * Verifica que todas las listas internas (filas) tengan la misma longitud.
     * Se asume que la primera fila define la forma de referencia. Si alguna de las demás filas
     * difiere en tamaño, se lanza una excepción indicando forma inválida.
     *
     * @param rows lista de listas que representan las filas del DataFrame
     * @throws InvalidShape si las filas no tienen tamaños consistentes entre sí
     */
    private void validateRowSize(List<? extends List<?>> rows) throws InvalidShape{
        //Si las filas tienen distinto tamaño se arroja una excepción
        int ref = rows.get(0).size();
        for (int i=1;i<rows.size();i++ ){
            if (rows.get(i).size()!=ref){
                throw new InvalidShape();
            }
        }
    }

    //Metodo aux. 1.2: ValidateRowShape
    /**
     *
     * Verifica que todas las filas tengan el mismo tamaño que la lista de
     * etiquetas de columna. Si alguna fila tiene un tamaño diferente, se lanza una excepción de forma inválida.
     *
     * @param rows lista de listas que representan las filas del DataFrame
     * @param columnLabels lista de etiquetas para las columnas, utilizada como referencia para validar la forma
     * @throws InvalidShape si alguna fila no tiene la misma cantidad de elementos que las etiquetas de columna
     */
    private void validateRowShape(List<? extends List<?>> rows, List<Label> columnLabels)throws InvalidShape{
        for (List<?> row : rows){
            if(row.size()!=columnLabels.size()){
                throw new InvalidShape();
            }
        }
    }

    //Metodo aux. 1.3: generateColumnLabels
    /**
     *
     * Crea una lista de etiquetas numéricas a partir del índice 1 hasta la cantidad de columnas menos uno.
     * Se asume que todas las filas tienen la misma cantidad de columnas. La primera columna (índice 0) se omite, ya que
     * puede estar reservada para otro propósito (como índice de filas).
     *
     * @param rows lista de listas que representan las filas del DataFrame; se toma la primera fila como referencia para contar las columnas
     * @return lista de etiquetas generadas automáticamente para las columnas
     */
    private List<Label> generateColumnLabels(List<? extends List<?>> rows){//argumento puede ser de tipo int: expectedSize
        int nCols = rows.get(0).size();
        List<Label> labels = new ArrayList<>();
        for(int i=1; i<nCols; i++){
            labels.add(new Label(i));
        }
        return labels;
    }

    //Metodo aux. 1.4: generateRowLabels
    /**
     *
     * Crea una lista de etiquetas numéricas a partir del índice 1 hasta la cantidad de filas.
     * Se asume que todas las filas tienen la misma cantidad de columnas. La primera columna (índice 0) se omite, ya que
     * puede estar reservada para otro propósito (como índice de filas).
     *
     * @param rows lista de listas que representan las filas del DataFrame; se toma la primera fila como referencia para contar las columnas
     * @return lista de etiquetas generadas automáticamente para las columnas
     */
    private List<Label> generateRowLabels(List<? extends List<?>> rows){//argumento puede ser de tipo int: expectedSize
        List<Label> labels = new ArrayList<>();
        for(int i=1; i<rows.size(); i++){
            labels.add(new Label(i));
        }
        return labels;
    }

    //Metodo aux. 1.5: fillColumns
    /**
     *
     * Completa la las columnas con sus valores correspondientes.
     * @param rows lista de listas que representan las celdas dentro del DataFrame
     * @param columnLabels lista de etiquetas para las columnas del DataFrame. (Encabezado)
     */
    private void fillColumns(List<? extends List<?>> rows, List<Label> columnLabels){
        for (int i = 0; i < columnLabels.size(); i++) {
            //Cargo el encabezado de la columna
            Label label = columnLabels.get(i);
            Column column = new Column(label);

            //Class<?> columnType = null;
            for (List<?> row : rows) {
                //Por cada fila en Lista<Lista> reviso solo el indice i.
                Object value = row.get(i);
                if (value == null || value.toString().equalsIgnoreCase("N/A")) {
                    column.addCell(new Cell(DataFrame.NA)); // valores faltantes tratados como null
                    continue; //Pasar a siguiente fila de datos.
                }

                if (!(value instanceof Number || value instanceof Boolean || value instanceof String)) {
                    throw new IllegalArgumentException("Tipo no soportado en la columna '" + label + "': " + value.getClass());
                }

                column.addCell(new Cell(value));
            }
            columns.add(column);
        }
    }

    //Metodo aux. 1.6: fillRows
    /**
     *
     * Completa la las filas con sus valores correspondientes: (numero indice, Label).
     * @param rowLabels lista de etiquetas que muestran los Rows.
     */
    private void fillRows(List<Label> rowLabels){
        //Se generan las filas a partir de los Labels de filas ordenados.
        for (int i = 0; i < rowLabels.size(); i++) {
            this.rows.add(new Row(rowLabels.get(i),i));
        }
    }

    //Metodo aux. 1.7: adaptarLabels
    /**
     *
     * Completa la las columnas con sus valores correspondientes.
     * @param labels lista de listas que representan las celdas dentro del DataFrame
     * @return aux lista de etiquetas para las columnas del DataFrame. (Encabezado)
     * @throws IllegalArgumentException los candidatos a labels no son ni String, ni Integer.
     */
    private List<Label> adaptarLabels(List<?> labels) throws IllegalArgumentException{

        List<Label> aux = new ArrayList<>();

        if (labels == null || labels.isEmpty()) {
            return aux; // Devuelve lista vacía (No deveria devolver error por querer modificar un label?)
        }

        for (Object l:labels){
            Label label = new Label(l);
            aux.add(label);
        }
        return aux;
    }


    //Constructor 5: Mapa de columnas
    /**
     * Constructor de la tabla a partir de una única columna de datos, una etiqueta de columna
     * y una lista de etiquetas de fila.
     *
     * @param columnMap Mapa que tiene como Keys las etiquetas de las columnas
     * @param rowLabels lista de etiquetas para las filas
     * @throws InvalidShape si la cantidad de valores (filas) entre distintas columnas no coinciden.
     */
    public DataFrame(Map<Object, List<Object>> columnMap, List<Object> rowLabels) throws InvalidShape {
        this();

        // Verifica que todas las columnas tengan misma cantidad de elementos (filas).
        //-1 es valor para inicializar el for, no tiene utilidad real.
        int expectedSize = -1;
        for (List<Object> column : columnMap.values()) {
            if (expectedSize == -1) {
                expectedSize = column.size();
            } else if (column.size() != expectedSize) {
                throw new InvalidShape("Las columnas no tienen la misma cantidad de filas.");
            }
        }

        int rowCount = expectedSize;

        // Convertimos los nombres de columnas a lista de Etiquetas
        List<?> columnLabels = columnMap.keySet()
                .stream()
                .map(Label::new)
                .toList();

        // Armamos la lista de listas con los datos (orientado a filas)
        List<List<Object>> data = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            List<Object> row = new ArrayList<>();
            for (Object colName : columnMap.keySet()) {
                //Arma Lista<Lista> con todos los elementos de una misma columna.
                row.add(columnMap.get(colName).get(i));
            }
            data.add(row);
        }

        // Convertimos etiquetas de columnas y filas a Labels
        List<Label> labelsC = adaptarLabels(columnLabels);
        List<Label> labelsR = adaptarLabels(rowLabels);

        generarDataFrame(data, labelsC, labelsR);
    }


    // Constructor 6: Copia por seleccion de un DataFrame
    public DataFrame(DataFrame other) {
        // Implementación: copiar columnas, celdas y filas según la lógica deseada (profunda o superficial)
    }

    /*
        Requerimientos pendientes:
            
        -Debe existir un valor especial que haga referencia a valores faltantes (N/A).
        -Una etiqueta (label) puede ser en formato numérico entero o cadena.
        -Dos columnas no pueden tener el mismo nombre. En caso contrario, tirar una excepción.
        -El dataFrame puede generarse a partir de un CSV: La idea es que el constructor convierta un .csv en
         una lista de listas que se pueda pasar como argumento a la función generateDataFrame.

        Cosas a corregir:
        -Modificar validateRows: determinar si es mejor que todas las rows sean del mismo tamaño
        o permitir que sean de tamaños distintos y que los datos que no tienen las filas
        más cortas sean completados por N/A
        - Pasar a columnLabels el numero de columnas como atributo
        */








    public void head(int n){
        int i = 0;
        int k = 0;
        String headers = "";
        for(Column c:columns){
            headers += "| "+c.getLabel().toString()+" | ";
        }
        System.out.println(headers);

        for(Row row: rows){
            if(k==rows.size()){break;}
            int j = row.getIndex();
            Label l = rows.get(k).getLabel();
            List<Cell> rowList = buildRow(k);
            System.out.println(l.toString()+": " + rowList.toString());
            k++;
        } 
    }

    private List<Cell> buildRow(int i){
        List<Cell> row = new ArrayList<>();
        for (Column c : columns){
            row.add(c.getCell(i));
        }
        return row;
    }




}
