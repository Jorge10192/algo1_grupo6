package tabla;
import tabla.Cell;

import java.util.List;

public class Row<T>{
    //Atributos
    private Label<T> label;
    private int index;

    //Constructor

    public Row (Label label, int index){
        this.label = label;
        this.index = index;
    }
    // Constructor copias
    public Row(Row other) {
        this.label = new Label<T>(other.getLabel());
        this.index = other.index;
    }
    //Getters

    public Label<T> getLabel(){
        return label;
    }
    public int getIndex(){
        return index;
    }
}
