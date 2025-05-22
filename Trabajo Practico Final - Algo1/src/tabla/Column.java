package tabla;

import java.util.ArrayList;
import java.util.List;

public class Column<T> {
    //Atributos
    private Object label;
    private List<Cell> cells;

    //Constructor
    public Column(Object label){
        this.label = label;
        this.cells = new ArrayList<>();
    }

    //Metodos
    public Object getLabel() {
        return this.label;
    }

    public void addCell(Cell cell){
        cells.add(cell);
    }

    public Cell getCell(int i){
        return cells.get(i);
    }

}
