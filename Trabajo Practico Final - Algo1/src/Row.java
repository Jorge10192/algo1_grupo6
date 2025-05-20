import java.util.List;

public class Row {
    private Object label;
    private List<Cell> cells;

    public Row(Object label, List<Cell> cells){
        this.label = label;
        this.cells =cells;
    }

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
}