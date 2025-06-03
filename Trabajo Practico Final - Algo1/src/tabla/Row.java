package tabla;
import tabla.Cell;

import java.util.List;

public class Row {
    //Atributos
    private Label label;
    private List<Cell> cells;
    private int index;
    //Constructor
    public Row(Label label, List<Cell> cells){
        this.label = label;
        this.cells =cells;
    }
    
    //Constructor por Strings
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Label: ").append(label != null ? label.toString() : "null").append(" | Cells: [");
        for (int i = 0; i < cells.size(); i++) {
            sb.append(cells.get(i));
            if (i < cells.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        return sb.toString();
    }

    //Metodos
    public List<Cell> getCells() {
        return this.cells;
    }
}
