public class Cell<T> {
    //Atributos
    private T value;

    //Constructor
    public Cell(T value) {
        this.value = value;
    }

    //Metodo getter
    public T getValue(){
        return value;
    }

    //Metodo setter
    public void setValue(T value){
        this.value = value;
    }

}
