package tabla;

import java.util.List;

public class DataFrameView {
    //Atributos

    private static int MAX_ROWS = 10;
    private static int MAX_COLS = 10;
    private static int MAX_CELL_CHARS = 15;
    
    //Constructor
    public DataFrameView(){

    }
    public DataFrameView(int MAX_ROWS, int MAX_COLS, int MAX_CELL_CHARS){
        this.MAX_ROWS = MAX_ROWS;
        this.MAX_COLS = MAX_COLS;
        this.MAX_CELL_CHARS = MAX_CELL_CHARS;
    }

    public static String formatTable(List<List<Cell>> rows, List<Label> rowLabels, List<Label> columnLabels) {
        StringBuilder sb = new StringBuilder();

        int totalRows = Math.min(rows.size(), MAX_ROWS);
        int totalCols = columnLabels != null ? Math.min(columnLabels.size(), MAX_COLS) : 0;

        // Formato de encabezado
        sb.append(padRight("", MAX_CELL_CHARS));  // espacio para la columna de etiquetas de fila
        for (int col = 0; col < totalCols; col++) {
            sb.append(padRight(truncate(columnLabels.get(col).toString()), MAX_CELL_CHARS));
        }
        if (columnLabels.size() > MAX_COLS) {
            sb.append("...");
        }
        sb.append("\n");

        // Formato de filas
        for (int row = 0; row < totalRows; row++) {
            // Etiqueta de fila
            String rowLabel = rowLabels != null && row < rowLabels.size()
                    ? truncate(rowLabels.get(row).toString())
                    : "";
            sb.append(padRight(rowLabel, MAX_CELL_CHARS));

            // Celdas
            List<Cell> rowCells = rows.get(row);
            for (int col = 0; col < totalCols && col < rowCells.size(); col++) {
                sb.append(padRight(truncate(rowCells.get(col).toString()), MAX_CELL_CHARS));
            }

            if (rowCells.size() > MAX_COLS) {
                sb.append("...");
            }
            sb.append("\n");
        }

        if (rows.size() > MAX_ROWS) {
            sb.append("...\n");
        }

        return sb.toString();
    }

    private static String truncate(String input) {
        return input.length() > MAX_CELL_CHARS ? input.substring(0, MAX_CELL_CHARS - 3) + "..." : input;
    }

    private static String padRight(String text, int length) {
        if (text.length() >= length) return text;
        return String.format("%-" + length + "s", text);
    }


}
