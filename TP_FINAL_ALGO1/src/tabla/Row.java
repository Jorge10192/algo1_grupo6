package tabla;

import java.util.List;
import java.util.ArrayList;

public class Row {

    //Atributos
    private String index;
    //No se reasigna una lista ya inicializada
    private final List<Cell> cells;

    // Constructor lista de valores cuando hay indice
    public Row(List<Object> filaDatos, int indiceColumna) {
        //Excepciones: No puede existir fila vacia
        if (filaDatos == null || filaDatos.isEmpty())
            throw new IllegalArgumentException("La fila no puede estar vacía");

        //Excepciones: La columna del indice debe tener una posicion valida en la tabla
        if (indiceColumna < 0 || indiceColumna >= filaDatos.size())
            throw new IllegalArgumentException("Indice columna fuera de rango");



        //Excepciones: El valor de un índice no puede ser nulo (indices mal rellenados)
        Object indiceValor = filaDatos.get(indiceColumna);

        if (indiceValor == null) {
            throw new IllegalArgumentException("El valor del índice no puede ser null");
        }
        this.index = indiceValor.toString();

        // Las celdas: Se cargan todos los elementos excepto el índice
        this.cells = new ArrayList<>();
        for (int i = 0; i < filaDatos.size(); i++) {
            if (i == indiceColumna) continue;  // Se saltea el índice
            Object valor = filaDatos.get(i);
            Class<?> tipo = (valor == null) ? Object.class : valor.getClass(); //if-else simplificado
            cells.add(new Cell(valor, tipo));
        }
    }

    //Constructor de Row que no posee indice asociado
    public Row(List<Cell> cells) {
        this.index = null;
        this.cells = cells;
    }

    //Metodos getters
    public List<Cell> getCells() {
        return cells;
    }

    public String getIndex() {
        return index;
    }

    //Metodos setters
    public void setIndex(String index) {
        this.index = index;
    }

    //Agregar celda (columna)
    public void addCell(Cell cell) {
        cells.add(cell);
    }

    //Devolver contenido de toda la lista.
    @Override
    public String toString() {
        return "Index: " + index + " - " + cells.toString();
    }

}
