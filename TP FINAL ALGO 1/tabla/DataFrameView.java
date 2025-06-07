package tabla;
import java.util.List;

public class DataFrameView {
    private final DataFrame source;
    private final List<Label<?>> selectedRows;
    private final List<Label<?>> selectedColumns;

    public DataFrameView(DataFrame source, List<Label<?>> rows, List<Label<?>> cols) {
        this.source = source;
        this.selectedRows = rows;
        this.selectedColumns = cols;
    }

    // Devuelve el valor de la celda (usando los métodos del DataFrame original)
    public Object getValue(Label<?> row, Label<?> col) {
        Cell<?> celda = source.obtenerCelda(row, col);
        return celda.getValue();
    }

    //Impresion de DataFrame en formato Tabular (tipo tabla con ancho fijo)
    public void imprimirTabular() {
        final int ANCHO = 15; // Puedes ajustar el ancho según tu necesidad
        String formato = "| %-" + ANCHO + "s";

        // Imprimir encabezados
        System.out.printf("%-" + ANCHO + "s", " "); // espacio para la columna de etiquetas de fila
        for (Label<?> col : selectedColumns) {
            System.out.printf(formato, col);
        }
        System.out.println("|");

        // Línea separadora
        int totalCols = selectedColumns.size() + 1;
        for (int i = 0; i < totalCols; i++) {
            System.out.print("+");
            for (int j = 0; j < ANCHO + 1; j++) System.out.print("-");
        }
        System.out.println("+");

        // Imprimir filas
        for (Label<?> row : selectedRows) {
            System.out.printf("%-" + ANCHO + "s", row);
            for (Label<?> col : selectedColumns) {
                Object valor = getValue(row, col);
                String cell = valor == null ? "" : valor.toString();
                System.out.printf(formato, cell);
            }
            System.out.println("|");
        }
    }

    // Métodos útiles extra (opcional)
    public List<Label<?>> getSelectedRows() { return selectedRows; }
    public List<Label<?>> getSelectedColumns() { return selectedColumns; }


    //---- Visualizacion del DataFrame ----

    public DataFrameView head(int n) {
        int k = Math.min(n, selectedRows.size());
        List<Label<?>> filas = selectedRows.subList(0, k);
        // Las columnas seleccionadas se mantienen igual
        return new DataFrameView(source, filas, selectedColumns);
    }

    public DataFrameView tail(int n) {
        int k = Math.min(n, selectedRows.size());
        List<Label<?>> filas = selectedRows.subList(selectedRows.size() - k, selectedRows.size());
        // Las columnas seleccionadas se mantienen igual
        return new DataFrameView(source, filas, selectedColumns);
    }



}