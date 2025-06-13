package Tabla;

public class Row {
    // Atributo
    private Label label;
    private int index;

    // Constructor
    public Row(Label label, int index){
        this.label = label;
        this.index = index;
    }

    // Getters
    public Label getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }

    // Metodos Generales
    public Row clonar(){
        return new Row(this.label.clonar(), this.index);
    }


}
