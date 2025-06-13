package tabla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.*;
import java.util.stream.Collectors;

import exceptions.*;

public class DataFrameHandler {
    
    //Atributo
    DataFrame df;

    //Constructor
    DataFrameHandler(DataFrame df){
        this.df =df;
    }

    //Comportamiento
    public void slice(List<?> columnLabels, List<?> rowLabels){
        
        List<Column> columnList = new ArrayList<>();
        List<List<Object>> rowList = new ArrayList<>();
        List<Label> cLabels = new ArrayList<>();
        List<Label> rLabels = new ArrayList<>();

        if(columnLabels==null || columnLabels.isEmpty()){
            columnLabels = df.getColumnLabels();
        }
        if(rowLabels==null || rowLabels.isEmpty() ){
            rowLabels = df.getRowLabels();  
        }
        
        for (Object l:columnLabels){
            Column c = df.obtenerColumna(l);
            columnList.add(c);
            cLabels.add(c.getLabel());
        }
         
        for (Object l:rowLabels){
            Row r = df.obtenerFila(l);
            rowList.add(df.buildRow(r.getIndex(),columnList));
            rLabels.add(r.getLabel());
        }
        
        DataFrameView tabla = new DataFrameView();
        System.out.println(tabla.formatTable(rowList, rLabels, cLabels));

    }

     /**
     * Filtra las filas del DataFrame basado en una o más condiciones.
     */
    public DataFrame filter(Map<Object, Predicate<Object>> conditions) {
        List<Row> rows = df.getRows();
        List<Column> columns = df.getColumns(); 
        List<List<Object>> filteredData = new ArrayList<>();
        

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            Row row = rows.get(rowIndex);
            boolean cumple = true;

            for (Map.Entry<Object, Predicate<Object>> entry : conditions.entrySet()) {
                Object colLabel = entry.getKey();
                Predicate<Object> predicate = entry.getValue();

                int colIndex = df.buscarColumna(new Label<>(colLabel));
                Cell<?> cell = columns.get(colIndex).getCell(rowIndex);
                if(cell.getValue() instanceof MissingValue){
                    cumple = false;
                    break;
                }
                if (!predicate.test(cell.getValue())) {
                    cumple = false;
                    break;
                }
            }

            if (cumple) {
                List<Object> filaValores = new ArrayList<>();
                for (Column<?> column : columns) {
                    filaValores.add(column.getCell(rowIndex).getValue());
                }
                filteredData.add(filaValores);
            }
        }

        List<Object> columnLabels = new ArrayList<>();
        for (Column<?> column : columns) {
            columnLabels.add(column.getLabel().getLabel());
        }

        try {
            return new DataFrame(filteredData, columnLabels, null);
        } catch (InvalidShape | IllegalArgumentException | InvalidTypeException e) {
            throw new RuntimeException("Error al construir el DataFrame filtrado: " + e.getMessage(), e);
        }
    }

        public DataFrame sortBy(List<? extends Object> labels, boolean descending) {

            List<Row> rows = df.getRows();
            List<Column> columns = df.getColumns(); 

            // Obtengo los índices de las columnas por las que quiero ordenar
            List<Integer> columnIndexes = new ArrayList<>();
            for (Object labelObj : labels) {
                Label label = new Label(labelObj);  // construimos un Label desde el objeto
                columnIndexes.add(df.buscarColumna(label));
            }

            // Creo la lista de pares (índice original, Row) para preservar la referencia a las celdas
            List<Row> sortedRows = new ArrayList<>(rows);

            // Comparador compuesto
            Comparator<Row> rowComparator = (r1, r2) -> {
                for (int colIdx : columnIndexes) {
                    Cell<?> c1 = columns.get(colIdx).getCell(r1.getIndex());
                    Cell<?> c2 = columns.get(colIdx).getCell(r2.getIndex());

                    Object v1 = c1.getValue();
                    Object v2 = c2.getValue();

                    // MissingValue va siempre al final
                    boolean isMissing1 = v1 instanceof MissingValue;
                    boolean isMissing2 = v2 instanceof MissingValue;

                    if (isMissing1 && isMissing2) continue;
                    if (isMissing1) return 1;
                    if (isMissing2) return -1;

                    // Comparación natural para Number, Integer, Boolean
                    int cmp;
                    if (v1 instanceof Comparable && v2 instanceof Comparable) {
                        cmp = ((Comparable) v1).compareTo(v2);
                    } else {
                        throw new IllegalArgumentException("No se puede comparar valores: " + v1 + " y " + v2);
                    }

                    if (cmp != 0) return descending ? -cmp : cmp;
                }
                return 0;
            };

            // Ordenamiento
            sortedRows.sort(rowComparator);

            // Se construye el DataFrame con filas ordenadas
            List<List<Object>> newData = new ArrayList<>();
            List<Object> rowLabels = new ArrayList<>();

            for (Row row : sortedRows) {
                List<Object> rowData = new ArrayList<>();
                for (Column<?> col : columns) {
                    rowData.add(col.getCell(row.getIndex()).getValue());
                }
                newData.add(rowData);
                rowLabels.add(row.getLabel().getLabel());
            }

            // Se extraen las etiquetas de columnas
            List<Object> columnLabels = columns.stream()
                                            .map(c -> c.getLabel().getLabel())
                                            .collect(Collectors.toList());

            try {
                return new DataFrame(newData, columnLabels, rowLabels);
            } catch (Exception e) {
                throw new RuntimeException("Error al construir el DataFrame ordenado: " + e.getMessage(), e);
            }
    }

    public DataFrame concatenar(DataFrame other){

        //Chequeo si la cantidad de columnas coinciden
        
        if(df.contarColumnas() != other.contarColumnas()){
            throw new InvalidShape("No se pueden concatenar los data-frames. La cantidad de columnas de ambos es distinta");
        }

        //Chequeo si los Labels coinciden y tipos de datos coinciden
        for (int i =0; i < df.contarColumnas();i++){
            Column col1 = df.getColumns().get(i);
            Column col2 = other.getColumns().get(i);

            if(!col1.matches(col2)){
                throw new RuntimeException("Las columnas de ambas tablas no coinciden en Label o en tipo de dato");
            }
        }
        
        DataFrame df1 = df.copy();
        DataFrame df2 = other.copy();
        
        List<List<Object>> data = new ArrayList<>();

        
        for (Row r:df1.getRows()){
            data.add(df1.buildRow(r.getIndex(),df1.getColumns()));
        }
        
        for (Row r:df2.getRows()){
            data.add(df2.buildRow(r.getIndex(),df2.getColumns()));
        }
        
        // Se extraen las etiquetas de columnas
            List<Object> columnLabels = df1.getColumns().stream()
                                            .map(c -> c.getLabel().getLabel())
                                            .collect(Collectors.toList());
        
        return new DataFrame(data,columnLabels,null);
    }

    public DataFrame sample(int n) throws IllegalArgumentException {
        List<Row> rows = df.getRows();
        List<Column> columns = df.getColumns();
        List<List<Object>> data = new ArrayList<>();

        if (n <= 0 || n > 100) {
            throw new IllegalArgumentException(n + " no es un número válido para porcentajes. Escribir únicamente valores entre 1 y 100");
        }

        int totalFilas = df.contarFilas();
        int size = Math.round((n / 100.0f) * totalFilas);

        // Creo la lista de índices para después mezclarla
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < totalFilas; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        // Tomo los primeros índices
        List<Integer> sampleIndices = indices.subList(0, size);

        for (int j : sampleIndices) {
            data.add(df.buildRow(rows.get(j).getIndex(), columns));
        }

        // etiquetas de columnas
        List<Object> columnLabels = columns.stream()
                .map(c -> c.getLabel().getLabel())
                .collect(Collectors.toList());

        return new DataFrame(data, columnLabels, null);
    }

}
