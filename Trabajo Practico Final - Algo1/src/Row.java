import java.util.List;

public class Row {
    private Object label;
    private List<Cell> cells;

    public Row(Object label, List<Cell> cells){
        this.label = label;
        this.cells =cells;
    }

    public Row(List<Cell> cells){
        this.cells =cells;
        //Agregar un método para crear labels de las filas
    }
}
