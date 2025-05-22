package tabla;

public class Cell {
    //Atributo
    private Object content;

    //Constructor
    public Cell(Object content){
        this.content = content;
    }

    //Devolver null, si el contenido es nulo.
    @Override
    public String toString() {
        return content != null ? content.toString() : "null";
    }

    //Metodos
    public Object getValue() {
        return this.content;
    }
}
