package tabla;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import exceptions.*;

public class DataFrame {

    private List<Column> columns;
    private List<Row> rows; 
    //Map<Label, Integer> rows el integer va a ser el indice de la fila  
    

    // Constructor por defecto
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    // Constructor desde matriz 2D
    public DataFrame(Object[][] array2D, List<Object> columnLabels, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException,InvalidTypeException {
        this();
        List<List<Object>> data = new ArrayList<>();
        for (Object[] row : array2D) {
            data.add(Arrays.asList(row));
        }

        List<Label> labelsC = adaptarLabels(columnLabels);
        List<Label> labelsR = adaptarLabels(rowLabels);

        generarDataFrame(data, labelsC, labelsR);
    }

    // Constructor desde lista de listas 
    public DataFrame(List<? extends List<?>> data, List<Object> columnLabels, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException,InvalidTypeException {
        this();

        List<Label> labelsC = adaptarLabels(columnLabels);
        List<Label> labelsR = adaptarLabels(rowLabels);

        generarDataFrame(data, labelsC, labelsR);
    }

    // Constructor desde una sola columna
    public DataFrame(List<Object> columnData, Object columnLabel, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException,InvalidTypeException {
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
    /* 
    // Constructor desde un mapa de columnas
    public DataFrame(Map<String, List<Object>> columnMap, List<Object> rowLabels) throws InvalidShape, InvalidTypeException {
        this();
        int rowCount = columnMap.values().iterator().next().size(); // asume columnas uniformes
        List<Object> labels = new ArrayList<>(columnMap.keySet());
        List<List<Object>> data = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            List<Object> row = new ArrayList<>();
            for (Object label : labels) {
                row.add(columnMap.get(label).get(i));
            }
            data.add(row);
        }
        generarDataFrame(data, labels, rowLabels);
    }*/

    // Constructor copia
    public DataFrame(DataFrame other) {

        // Copia profunda
        
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();

        for (Column col : other.columns) {
            this.columns.add(new Column(col)); 
        }

        for (Row row : other.rows) {
            this.rows.add(new Row(row)); 
        }
    }
        
    


    // Método interno para poblar el dataframe
    private void generarDataFrame(List<? extends List<?>> rows, List<Label> columnLabels, List<Label> rowLabels) throws InvalidShape, InvalidTypeException, IllegalArgumentException {
        //Verifica que todas las listas dentro de rows tengan el mismo tamaño
        validateRowSize(rows);

        //Para la lista de filas, se verifica que el largo de cada fila coincida con la cantidad de headers de columnas
        if(columnLabels!=null){
            validateRowShape(rows, columnLabels);
        }

        //Manejar labels de columnas: generar nuevos si no label=null, o validar que las labels sean consistentes con la data
        if(columnLabels == null || columnLabels.isEmpty()){
            columnLabels = generateColumnLabels(rows);
        }
        //Manejar labels de filas: generar nuevos si no label=null, o validar que las labels sean consistentes con la data
        if(rowLabels == null || rowLabels.isEmpty()){
            rowLabels = generateRowLabels(rows);
        }
        //Evita que dos Labels sean iguales
        validarLabelsUnicos(columnLabels);
        validarLabelsUnicos(rowLabels);
        
        fillColumns(rows, columnLabels);
        fillRows(rowLabels);
    }

    private void validateRowSize(List<? extends List<?>> rows) throws InvalidShape{
        //Si las filas tienen distinto tamaño se arroja una excepción
        int ref =rows.get(0).size();
        for (int i=1;i<rows.size();i++ ){
            if (rows.get(i).size()!=ref){
                throw new InvalidShape();
            }
        }
    }

    private void validateRowShape(List<? extends List<?>> rows, List<Label> columnLabels)throws InvalidShape{
        for (List<?> row : rows){
            if(row.size()!=columnLabels.size()){
                throw new InvalidShape();
            }
        }
    }

    private List<Label> generateColumnLabels(List<? extends List<?>> rows){//argumento puede ser de tipo int: expectedSize
        int nCols = rows.get(0).size();
        List<Label> labels = new ArrayList<>();
        for(int i=0; i<nCols; i++){
            labels.add(new Label(i));
        }
        return labels;
    }

    private List<Label> generateRowLabels(List<? extends List<?>> rows){//argumento puede ser de tipo int: expectedSize
        List<Label> labels = new ArrayList<>();
        for(int i=0; i<rows.size(); i++){
            labels.add(new Label(i));
        }
        return labels;
    }

    private static void validarLabelsUnicos(List<Label> labels) throws IllegalArgumentException {

        Set<Object> valoresVistos = new HashSet<>();

        for (Label<?> label : labels) {
            Object valor = label.getLabel();
            if (!valoresVistos.add(valor)) {
                throw new IllegalArgumentException("No pueden haber dos labels con el mismo nombre: " + valor);
            }
        }
    }
    

    private void fillColumns(List<? extends List<?>> rows, List<Label> columnLabels)throws InvalidTypeException{
        for (int i = 0; i < columnLabels.size(); i++) {
            Label label = columnLabels.get(i);
            Column column = new Column(label);

            
            for (List<?> row : rows) {
                Object value = row.get(i);
                if (value == null || value.toString().equalsIgnoreCase("N/A")) {
                    column.addCell(new Cell(new MissingValue())); // valores faltantes tratados como null
                    continue;
                }

                if (!(value instanceof Number || value instanceof Boolean || value instanceof String)) {
                    throw new IllegalArgumentException("Tipo no soportado en la columna '" + label + "': " + value.getClass());
                }
                
                column.addCell(new Cell(value));
            }
            columns.add(column);
        }
    }


    private void fillRows(List<Label> rowLabels){
        for (int i = 0; i < rowLabels.size(); i++) {
            this.rows.add(new Row(rowLabels.get(i),i));
        }
    }

    private List<Label> adaptarLabels(List<?> labels) throws IllegalArgumentException{
        
        List<Label> aux = new ArrayList<>();

        if (labels == null || labels.isEmpty()) {
            return aux; // Devuelve lista vacía
        }

        for (Object l:labels){
            Label label = new Label(l);
            aux.add(label);
        }
        return aux;
    }

    public static final Object NA = new Object() {
    @Override
        public String toString() {
            return "N/A";
        }
    };

    public void head(int n){
        
        int k = 0;
        List<Label> colLabels = new ArrayList<>();
        List<Label> rowLabels = new ArrayList<>();
        List<List<Cell>> ListOfRows = new ArrayList<>();

        for(Column c:columns){
            colLabels.add(c.getLabel());
        }
        for(Row row: rows){
           if(k==n){break;}
            int j = row.getIndex();
            rowLabels.add(rows.get(k).getLabel());
            List<Cell> rowList = buildRow(j);
            ListOfRows.add(rowList);
            k++;
        }

        DataFrameView tabla = new DataFrameView();
        System.out.println(tabla.formatTable(ListOfRows, rowLabels, colLabels));
    }

    public void tail(int n) {
        
        int totalRows = rows.size();
        int start = Math.max(0, totalRows - n); // En caso de que n > totalRows
        
        List<Label> colLabels = new ArrayList<>();
        List<Label> rowLabels = new ArrayList<>();
        List<List<Cell>> listOfRows = new ArrayList<>();

        for (Column c : columns) {
            colLabels.add(c.getLabel());
        }

        for (int i = start; i < totalRows; i++) {
            Row row = rows.get(i);
            int j = row.getIndex();
            rowLabels.add(row.getLabel());
            List<Cell> rowList = buildRow(j);
            listOfRows.add(rowList);
        }

        DataFrameView tabla = new DataFrameView();
        System.out.println(tabla.formatTable(listOfRows, rowLabels, colLabels));
    }


    private List<Cell> buildRow(int i){
        List<Cell> row = new ArrayList<>();
        for (Column c : columns){
            row.add(c.getCell(i));
        }
        return row;
    }

    public int contarColumnas(){
        if(columns==null){
            return 0;
        }
        return columns.size(); 
    }

    public int contarFilas(){
        if(rows==null){
            return 0;
        }
        return rows.size();
    }
    public void info(){

        System.out.println(" \n" + "Data columns: total "+columns.size());

        for (Column c : columns){

            Label label = c.getLabel();
            int na = c.countNA();
            Class<?> tipo =c.getType();

            System.out.println(label + ": "+ (columns.size()-na) + " non-null, "+ tipo);
        }
    }

    public List<Cell> obtenerFila(Label label){
    //Que pasa si la Label no está entre las rows
    //Admitir valores de tipo int o String, ademas de Label
    //  
        int index = 0;      
        for(Row r : rows){
            index = buscarFila(label);
        }
        Row row = rows.get(index);
        List<Cell> lista = new ArrayList<>();
        for(Column c : columns){
            lista.add(c.getCell(index));
        }
        return lista;
    }

    private int buscarFila(Label label){
        int i=0;
        for(Row r: rows){
            //Sobreescribir equals de Label
            if (label.equals(r.getLabel())){
                return i; 
            }
            i++;
        }
        throw new RuntimeException("");
    }






}
