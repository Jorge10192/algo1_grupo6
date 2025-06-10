package tabla;

public class Cell<T>{
    //Atributo
    private T content;

    //Constructor
    public Cell(T content){
        this.content = content;
    }
    // Constructor copia
    public Cell(Cell<T> other) {
        this.content = other.content;
    }
    //Devolver null, si el contenido es nulo.
    @Override
    public String toString() {
        return content != null ? content.toString() : "null";
    }

    //Metodos
    public T getValue() {
        return this.content;
    }
    public void setValue(T content){
        this.content = content;
    }
}
