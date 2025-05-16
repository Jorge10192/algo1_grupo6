package tabla;

import java.util.List;
import java.util.ArrayList;

public class Row {

    //Atributos
    private int id;
    private List<Cell> cells;

    //Constructor con solo id
    public Row(int id) {
        this.id = id;
        this.cells = new ArrayList<>();
    }
    // Constructor adicional con id y lista de valores
    public Row(int id, List<Cell> cells) {
        this.id = id;
        this.cells = cells;
    }

    //Metodos getters
    public int getId() {
        return id;
    }

    public List<Cell> getCells() {
        return cells;
    }

    //Agregar celda (columna)
    public void addCell(Cell cell) {
        cells.add(cell);
    }

    //Devolver contenido de toda la lista.
    @Override
    public String toString() {
        return cells.toString();
    }
}
