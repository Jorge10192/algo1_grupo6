package tabla;
import tabla.Cell;

import java.util.List;

public class Row {
    //Atributos
    private Label label;
    private int index;

    //Constructor
    public Row (Label label, int index){
        this.label = label;
        this.index = index;
    }

    //Getters
    public Label getLabel(){
        return label;
    }
    public int getIndex(){
        return index;
    }

}
