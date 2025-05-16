package tabla;

import java.util.List;
import java.util.ArrayList;

public class DataFrame {

    //Atributos
    private List<Column> columns;
    private List<Row> rows;

    //Constructor
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    //Añadir columna
    public void addColumn(Column column) {
        columns.add(column);
    }

    //Añadir fila
    public void addRow(Row row) {
        rows.add(row);
    }

    //getter columnas
    public List<Column> getColumns() {
        return columns;
    }

    //getter filas
    public List<Row> getRows() {
        return rows;
    }

    //Buscar columna por nombre
    public Column getColumnByName(String name) {
        for (Column column : columns) {
            if (column.getName().equals(name)) {
                return column;
            }
        }
        return null; // o lanzar una excepción
    }

    //Imprimir DataFrame completo
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Encabezado de Columnas (con ID adicional al comienzo del header)
        sb.append("ID\t");
        for (Column column : columns) {
            sb.append(column.getName()).append("\t");
        }
        sb.append("\n");

        // Filas, evitando dependencias sobre Row para agregar estilos despues.
        for (Row row : rows) {
            //Obtener primero el ID
            sb.append(row.getId()).append("\t");
            //Imprimir contenido de la Fila
            for (Cell cell : row.getCells()) {
                sb.append(cell.getValue()).append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
