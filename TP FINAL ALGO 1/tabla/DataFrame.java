package tabla;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Comparator;
import java.util.Collections;


import exceptions.*;

public class DataFrame {

    private List<Column> columns;
    private List<Row> rows;

    // Constructor por defecto
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    // Constructor desde matriz 2D
    public DataFrame(Object[][] array2D, List<Object> columnLabels, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException, InvalidTypeException {
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
    public DataFrame(List<? extends List<?>> data, List<Object> columnLabels, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException, InvalidTypeException {
        this();
        List<Label> labelsC = adaptarLabels(columnLabels);
        List<Label> labelsR = adaptarLabels(rowLabels);
        generarDataFrame(data, labelsC, labelsR);
    }

    // Constructor desde una sola columna
    public DataFrame(List<Object> columnData, Object columnLabel, List<Object> rowLabels) throws InvalidShape, IllegalArgumentException, InvalidTypeException {
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


    // Constructor copia profunda.
    public DataFrame(DataFrame original) {
        this.columns = new ArrayList<>();
        for (Column col : original.columns) {
            Column nuevaCol = new Column(col.getLabel());
            for (int i = 0; i < col.size(); i++) {
                Cell<?> originalCell = col.getCell(i);
                Object valor = originalCell.getValue();
                // Si el valor es mutable y debe copiarse, hacelo aquí.
                nuevaCol.addCell(new Cell<>(valor));
            }
            this.columns.add(nuevaCol);
        }

        this.rows = new ArrayList<>();
        for (Row row : original.rows) {
            this.rows.add(new Row(row.getLabel(), row.getIndex()));
        }
    }

    // Construye el dataframe interno
    private void generarDataFrame(List<? extends List<?>> filas, List<Label> columnLabels, List<Label> rowLabels) throws InvalidShape, InvalidTypeException {
        validateRowSize(filas);
        if (columnLabels != null) {
            validateRowShape(filas, columnLabels);
        }
        if (columnLabels == null || columnLabels.isEmpty()) {
            columnLabels = generateColumnLabels(filas);
        }
        if (rowLabels == null || rowLabels.isEmpty()) {
            rowLabels = generateRowLabels(filas);
        }
        fillColumns(filas, columnLabels);
        fillRows(rowLabels);
    }

    private void validateRowSize(List<? extends List<?>> filas) throws InvalidShape {
        int ref = filas.get(0).size();
        for (int i = 1; i < filas.size(); i++) {
            if (filas.get(i).size() != ref) {
                throw new InvalidShape();
            }
        }
    }

    private void validateRowShape(List<? extends List<?>> filas, List<Label> columnLabels) throws InvalidShape {
        for (List<?> row : filas) {
            if (row.size() != columnLabels.size()) {
                throw new InvalidShape();
            }
        }
    }

    private List<Label> generateColumnLabels(List<? extends List<?>> filas) {
        int nCols = filas.get(0).size();
        List<Label> labels = new ArrayList<>();
        for (int i = 0; i < nCols; i++) {
            labels.add(new Label(i));
        }
        return labels;
    }

    private List<Label> generateRowLabels(List<? extends List<?>> filas) {
        List<Label> labels = new ArrayList<>();
        for (int i = 0; i < filas.size(); i++) {
            labels.add(new Label(i));
        }
        return labels;
    }

    // Chequea columnas duplicadas y llena las columnas
    private void fillColumns(List<? extends List<?>> filas, List<Label> columnLabels) throws InvalidTypeException {
        this.columns.clear();
        for (Label label : columnLabels) {
            if (columns.stream().anyMatch(col -> col.getLabel().equals(label))) {
                throw new IllegalArgumentException("Duplicated column label: " + label);
            }
            columns.add(new Column(label));
        }
        for (int rowIdx = 0; rowIdx < filas.size(); rowIdx++) {
            List<?> fila = filas.get(rowIdx);
            for (int colIdx = 0; colIdx < columnLabels.size(); colIdx++) {
                Object value = fila.get(colIdx);
                Column column = columns.get(colIdx);
                if (value == null || value.toString().equalsIgnoreCase("N/A")) {
                    column.addCell(new Cell<>(new MissingValue()));
                } else if (!(value instanceof Number || value instanceof Boolean || value instanceof String)) {
                    throw new IllegalArgumentException("Tipo no soportado en la columna '" + columnLabels.get(colIdx) + "': " + value.getClass());
                } else {
                    column.addCell(new Cell<>(value));
                }
            }
        }
    }

    private void fillRows(List<Label> rowLabels) {
        this.rows.clear();
        for (int i = 0; i < rowLabels.size(); i++) {
            this.rows.add(new Row(rowLabels.get(i), i));
        }
    }

    private List<Label> adaptarLabels(List<?> labels) {
        List<Label> aux = new ArrayList<>();
        if (labels == null || labels.isEmpty()) {
            return aux;
        }
        for (Object l : labels) {
            aux.add(new Label(l));
        }
        return aux;
    }

    public static final Object NA = new Object() {
        @Override
        public String toString() {
            return "N/A";
        }
    };

    // --- Métodos accesorios a la visualización ---


    private List<Cell<?>> buildRow(int i) {
        List<Cell<?>> row = new ArrayList<>();
        for (Column c : columns) {
            row.add(c.getCell(i));
        }
        return row;
    }

    private List<Cell<?>> buildColumn(int colIdx) {
        List<Cell<?>> columna = new ArrayList<>();
        for (int fila = 0; fila < rows.size(); fila++) {
            columna.add(columns.get(colIdx).getCell(fila));
        }
        return columna;
    }

    public int contarColumnas() {
        return columns == null ? 0 : columns.size();
    }

    public int contarFilas() {
        return rows == null ? 0 : rows.size();
    }


    // --- SELECCIÓN DE ELEMENTOS ---

    /** Busca el índice de una fila a partir de su Label */
    private int buscarFila(Label<?> label) {
        for (int i = 0; i < rows.size(); i++) {
            if (label.equals(rows.get(i).getLabel())) {
                return i;
            }
        }
        throw new RuntimeException("Fila no encontrada: " + label);
    }

    /** Busca el índice de una columna a partir de su Label */
    private int buscarColumna(Label<?> label) {
        for (int i = 0; i < columns.size(); i++) {
            if (label.equals(columns.get(i).getLabel())) {
                return i;
            }
        }
        throw new RuntimeException("Columna no encontrada: " + label);
    }

    /** Devuelve el Label de la fila dado su índice */
    public Label<?> obtenerLabelFilaPorIndice(int filaIdx) {
        if (filaIdx < 0 || filaIdx >= rows.size()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango: " + filaIdx);
        }
        return rows.get(filaIdx).getLabel();
    }

    /** Devuelve el Label de la columna dado su índice */
    public Label<?> obtenerLabelColumnaPorIndice(int colIdx) {
        if (colIdx < 0 || colIdx >= columns.size()) {
            throw new IndexOutOfBoundsException("Índice de columna fuera de rango: " + colIdx);
        }
        return columns.get(colIdx).getLabel();
    }

    // --- Seleccionar Filas, Columnas y Celdas por Labels e Indices numericos ---

    // Selecciona una fila completa por Label
    public List<Cell<?>> obtenerFila(Label<?> filaLabel) {
        int filaIdx = buscarFila(filaLabel);
        return buildRow(filaIdx);
    }

    // Selecciona una fila completa por índice
    public List<Cell<?>> obtenerFilaPorIndice(int filaIdx) {
        if (filaIdx < 0 || filaIdx >= rows.size()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango: " + filaIdx);
        }
        return buildRow(filaIdx);
    }

    // Selecciona una columna completa por Label
    public List<Cell<?>> obtenerColumna(Label<?> colLabel) {
        int colIdx = buscarColumna(colLabel);
        return buildColumn(colIdx);
    }

    // Selecciona una columna completa por índice
    public List<Cell<?>> obtenerColumnaPorIndice(int colIdx) {
        if (colIdx < 0 || colIdx >= columns.size()) {
            throw new IndexOutOfBoundsException("Índice de columna fuera de rango: " + colIdx);
        }
        return buildColumn(colIdx);
    }

    // Selecciona una celda por Label de fila y columna
    public Cell<?> obtenerCelda(Label<?> filaLabel, Label<?> colLabel) {
        int filaIdx = buscarFila(filaLabel);
        int colIdx = buscarColumna(colLabel);
        return columns.get(colIdx).getCell(filaIdx);
    }

    // Selecciona una celda por índices de fila y columna
    public Cell<?> obtenerCeldaPorIndice(int filaIdx, int colIdx) {
        if (filaIdx < 0 || filaIdx >= rows.size()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango: " + filaIdx);
        }
        if (colIdx < 0 || colIdx >= columns.size()) {
            throw new IndexOutOfBoundsException("Índice de columna fuera de rango: " + colIdx);
        }
        return columns.get(colIdx).getCell(filaIdx);
    }

    //---- EJERCICIO: OBTENER SELECCION PARCIAL POR FILAS Y COLUMNAS (POR INDICE Y ETIQUETAS) (Sin copiar los datos) ----
    // Selección múltiple: submatriz (filas y columnas)
    public List<List<Cell<?>>> obtenerSeleccion(List<Label<?>> filas, List<Label<?>> columnas) {
        List<List<Cell<?>>> seleccion = new ArrayList<>();

        //Selecciona una fila dentro del DataFrame
        for (Label<?> filaLabel : filas) {
            int filaIdx = buscarFila(filaLabel);
            List<Cell<?>> filaSeleccionada = new ArrayList<>();
            //Recorre todas las columnas de la fila seleccionada
            for (Label<?> colLabel : columnas) {
                int colIdx = buscarColumna(colLabel);
                filaSeleccionada.add(columns.get(colIdx).getCell(filaIdx));
            }
            seleccion.add(filaSeleccionada);
        }
        return seleccion;
    }

    public List<List<Cell<?>>> obtenerSeleccionPorIndices(List<Integer> filasIdx, List<Integer> columnasIdx) {
        List<List<Cell<?>>> seleccion = new ArrayList<>();
        for (int filaIdx : filasIdx) {
            List<Cell<?>> filaSeleccionada = new ArrayList<>();
            for (int colIdx : columnasIdx) {
                filaSeleccionada.add(columns.get(colIdx).getCell(filaIdx));
            }
            seleccion.add(filaSeleccionada);
        }
        return seleccion;
    }


    //---- EJERCICIO: INFO, HEAD Y TAIL ----
    public DataFrameView head(int n) {
        int k = Math.min(n, rows.size());
        List<Label<?>> filas = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            filas.add(rows.get(i).getLabel());
        }
        List<Label<?>> columnas = new ArrayList<>();
        for (Column c : columns) {
            columnas.add(c.getLabel());
        }
        return new DataFrameView(this, filas, columnas);
    }

    public DataFrameView tail(int n) {
        int k = Math.min(n, rows.size());
        List<Label<?>> filas = new ArrayList<>();
        for (int i = rows.size() - k; i < rows.size(); i++) {
            filas.add(rows.get(i).getLabel());
        }
        List<Label<?>> columnas = new ArrayList<>();
        for (Column c : columns) {
            columnas.add(c.getLabel());
        }
        return new DataFrameView(this, filas, columnas);
    }

    //---- EJERCICIO: Impresion de informción del DataFrame ----
    public String info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cantidad de filas: ").append(rows.size()).append("\n");
        sb.append("Cantidad de columnas: ").append(columns.size()).append("\n");

        sb.append("Etiquetas de filas: ");
        for (Row row : rows) {
            sb.append(row.getLabel()).append(" ");
        }
        sb.append("\n");

        sb.append("Etiquetas y tipos de columnas:\n");
        sb.append("| Etiqueta\t| Tipo de dato\t|\n");
        sb.append("|-----------\t|--------------\t|\n");
        for (Column col : columns) {
            sb.append("| ")
                    .append(col.getLabel()).append("\t| ")
                    .append(col.getType().getSimpleName()).append("\t|\n");
        }

        return sb.toString();
    }


    //---- EJERCICIO: ARMADO DE FILTROS ----
    public List<Label<?>> filtrarFilas(Filtro filtro) {
        List<Label<?>> filasFiltradas = new ArrayList<>();
        for (Row row : rows) {
            if (filtro.test(this, row.getLabel())) {
                filasFiltradas.add(row.getLabel());
            }
        }
        return filasFiltradas;
    }

    public DataFrameView filtrarVista(Filtro filtro) {
        List<Label<?>> filasFiltradas = new ArrayList<>();
        for (Row row : rows) {
            if (filtro.test(this, row.getLabel())) {
                filasFiltradas.add(row.getLabel());
            }
        }
        List<Label<?>> columnas = columns.stream()
                .map(col -> (Label<?>) col.getLabel())
                .collect(java.util.stream.Collectors.toList());
        return new DataFrameView(this, filasFiltradas, columnas);
    }

    //---- EJERCICIO EDICION: AGREGAR / QUITAR FILAS Y COLUMNAS A UN DATAFRAME EXISTENTE ----
    //Agregar columna desde lista
    public <T> void agregarColumnaDesdeLista(Label<T> label, List<T> datos) {
        if (datos.size() != rows.size()) {
            throw new IllegalArgumentException("La lista debe tener la misma cantidad de elementos que filas.");
        }
        Column<T> nuevaCol = new Column<>(label);
        for (T valor : datos) {
            nuevaCol.addCell(new Cell<>(valor)); // Asegurate que Column y Cell tengan estos métodos
        }
        columns.add(nuevaCol);
    }
    //Agregar columna desde otra columna copiada
    public <T> void agregarColumna(Column<T> columna) {
        // Verifica que la columna tenga el tamaño correcto
        if (columna.size() != rows.size()) {
            throw new IllegalArgumentException("La columna debe tener la misma cantidad de elementos que filas.");
        }
        // Clona la columna para evitar referencias compartidas (opcional pero recomendable)
        Column<T> copia = columna.clonar(); // Necesitás implementar un método clonar en Column
        columns.add(copia);
    }

    //Agregar fila
    public void agregarFila(Label<?> label, List<?> valores) {
        if (valores.size() != columns.size()) {
            throw new IllegalArgumentException("La cantidad de valores debe ser igual a la cantidad de columnas.");
        }
        rows.add(new Row(label, rows.size())); // suponiendo que Row tiene ese constructor
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).addCellFromObject(valores.get(i)); // Necesitás este método para castear y agregar
        }
    }

    //Eliminar columna
    public void eliminarColumnaPorIndice(int indice) {
        columns.remove(indice);
    }

    public void eliminarColumnaPorLabel(Label<?> label) {
        columns.removeIf(col -> col.getLabel().equals(label));
    }

    //Eliminar fila
    public void eliminarFilaPorIndice(int indice) {
        rows.remove(indice);
        for (Column<?> col : columns) {
            col.removeCell(indice); // Necesitás este método en Column
        }
    }

    public void eliminarFilaPorLabel(Label<?> label) {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getLabel().equals(label)) {
                eliminarFilaPorIndice(i);
                break;
            }
        }
    }


    //---- EJERCICIO: ORDENAR DATAFRAME ----.
    /**
     * Devuelve un nuevo DataFrame con las filas ordenadas según los labels de columna indicados.
     * El orden de los labels define la precedencia. El booleano indica sentido (ascendente/descendente).
     * No modifica el DataFrame original.
     *
     * @param columnLabels Lista de labels de columna para el criterio de ordenamiento (en orden de precedencia)
     * @param ascending true para ascendente, false para descendente
     * @return Nuevo DataFrame con las filas ordenadas
     */
    public DataFrame ordenarFilas(List<Object> columnLabels, boolean ascending) {
        // 1. Obtener los índices de las columnas para ordenar
        List<Integer> colIndexes = new ArrayList<>();
        for (Object colLabel : columnLabels) {
            // Buscar el índice en columns
            int idx = -1;
            for (int j = 0; j < contarColumnas(); j++) {
                if (obtenerLabelColumnaPorIndice(j).equals(new Label<>(colLabel))) {
                    idx = j;
                    break;
                }
            }
            if (idx == -1) throw new IllegalArgumentException("Columna no encontrada: " + colLabel);
            colIndexes.add(idx);
        }

        // 2. Construir lista de pares (label de fila, fila de datos)
        List<Pair<Label<?>, List<Cell<?>>>> filas = new ArrayList<>();
        for (int i = 0; i < contarFilas(); i++) {
            Label<?> rowLabel = obtenerLabelFilaPorIndice(i);
            List<Cell<?>> fila = obtenerFilaPorIndice(i);
            filas.add(new Pair<>(rowLabel, fila));
        }

        // 3. Ordenar usando un Comparator por las columnas indicadas
        Comparator<Pair<Label<?>, List<Cell<?>>>> comp = (a, b) -> {
            for (int idx : colIndexes) {
                Object valA = a.getValue().get(idx).getValue();
                Object valB = b.getValue().get(idx).getValue();

                // Manejo de nulls
                if (valA == null && valB == null) continue;
                if (valA == null) return ascending ? 1 : -1;
                if (valB == null) return ascending ? -1 : 1;

                // Comparación normal
                int cmp = ((Comparable) valA).compareTo(valB);
                if (cmp != 0) {
                    return ascending ? cmp : -cmp;
                }
            }
            return 0;
        };
        filas.sort(comp);

        // 4. Reconstruir nuevas listas de datos y labels
        List<List<Object>> datosOrdenados = new ArrayList<>();
        List<Object> rowLabelsOrdenados = new ArrayList<>();
        for (Pair<Label<?>, List<Cell<?>>> par : filas) {
            List<Object> filaObj = new ArrayList<>();
            for (Cell<?> c : par.getValue()) {
                filaObj.add(c.getValue());
            }
            datosOrdenados.add(filaObj);
            rowLabelsOrdenados.add(par.getKey().getLabel());
        }
        // Column labels originales
        List<Object> colLabels = new ArrayList<>();
        for (int i = 0; i < contarColumnas(); i++) {
            colLabels.add(obtenerLabelColumnaPorIndice(i).getLabel());
        }

        try {
            return new DataFrame(datosOrdenados, colLabels, rowLabelsOrdenados);
        } catch (Exception e) {
            throw new RuntimeException("Error al ordenar el DataFrame: " + e.getMessage(), e);
        }
    }

    // Clase auxiliar Pair (Seleccionar pares para armar columna (Label, Lista de Celdas)
    private static class Pair<K, V> {
        private final K key;
        private final V value;
        public Pair(K k, V v) { key = k; value = v; }
        public K getKey() { return key; }
        public V getValue() { return value; }
    }


}