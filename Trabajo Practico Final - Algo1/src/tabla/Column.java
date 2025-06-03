package tabla;

import java.util.ArrayList;
import java.util.List;

public class Column<T> {
    //Atributos
    private Label label;
    private List<Cell<T>> cells;

    //Constructor
    public Column(Label label){
        this.label = label;
        this.cells = new ArrayList<>();
    }

    //Metodos
    public Label getLabel() {
        return this.label;
    }

    public void addCell(Cell<T> cell){
        cells.add(cell);
    }

    public Cell<T> getCell(int i){
        return cells.get(i);
    }

}
