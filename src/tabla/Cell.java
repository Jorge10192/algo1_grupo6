package tabla;

public class Cell<T> {
    // Atributo
    private T value;

    // Constructor
    public Cell(T value){
        this.value = value;
    }

    // Getters
    public T getValue() {
        return value;
    }

    // Setter
    public void setValue(T value) {
        this.value = value;
    }

    // Metodo de impresion
    //Devolver null, si el contenido es nulo.
    @Override
    public String toString(){
        return value != null ? value.toString() : "null";
    }
}
