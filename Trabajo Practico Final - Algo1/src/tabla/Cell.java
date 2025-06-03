package tabla;

public class Cell<T>{
    //Atributo
    private T content;

    //Constructor
    public Cell(T content){
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
    public void setValue(T content){
        this.content = content;
    }
}
