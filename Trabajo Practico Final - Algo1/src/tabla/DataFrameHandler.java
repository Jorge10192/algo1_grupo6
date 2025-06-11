package tabla;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.*;

import exceptions.*;

public class DataFrameHandler {
    DataFrame df;
    DataFrameHandler(DataFrame df){
        this.df =df;
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
}
