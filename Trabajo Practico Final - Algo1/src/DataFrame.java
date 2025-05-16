import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

public class DataFrame {

    private List<Column> columns;
    private List<Row> rows;
    private int nRows;

    // Constructor por defecto
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.nRows = 0;
    }

    // Constructor desde matriz 2D
    public DataFrame(Object[][] array2D, List<Object> labels) {
        this();
        List<List<Object>> data = new ArrayList<>();
        for (Object[] row : array2D) {
            data.add(Arrays.asList(row));
        }
        generarDataFrame(data, labels);
    }

    // Constructor desde lista de listas 
    public DataFrame(List<List<Object>> data, List<Object> labels) {
        this();
        generarDataFrame(data, labels);
    }

    // Constructor desde una sola columna
    public DataFrame(List<Object> columnData, Object label) {
        this();
        List<List<Object>> data = new ArrayList<>();
        for (Object value : columnData) {
            List<Object> row = new ArrayList<>();
            row.add(value);
            data.add(row);
        }
        generarDataFrame(data, List.of(label));
    }

    // Constructor desde un mapa de columnas
    public DataFrame(Map<String, List<Object>> columnMap) {
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

        generarDataFrame(data, labels);
    }

    // Constructor copia
    public DataFrame(DataFrame other) {
        // Implementación: copiar columnas, celdas y filas según la lógica deseada (profunda o superficial)
    }

    // Método interno para poblar el dataframe
    private void generarDataFrame(List<List<Object>> rows, List<Object> labels) {
        this.nRows = rows.size();
        //Se crea una columna para cada índice de las filas
        for (int i = 0; i < labels.size(); i++) {
            Column column = new Column(labels.get(i));
            for (List<Object> row : rows) {
                column.addCell(new Cell(row.get(i)));
            }
            columns.add(column);
        }
        //Se recorre cada columna para generar las filas, esta vez las filas son listas que guardan la referencia a las celdas
        for (int i = 0; i < nRows; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (Column column : columns) {
                cellList.add(column.getCell(i));
            }
            this.rows.add(new Row(i, cellList));
        }
    }
}