package tabla;

public class Cell<T>{
    //Atributo
    private T content;

    //Constructor general
    public Cell(T content){
        this.content = content;
    }

    //getter
    public Object getValue() {
        return this.content;
    }

    //setter
    public void setValue(T content){
        this.content = content;
    }

    //Metodo: Devolver null, si el contenido es nulo.
    @Override
    public String toString() {
        return content != null ? content.toString() : "null";
    }

}
