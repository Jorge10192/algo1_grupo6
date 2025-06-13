package Tabla;

public class Label<T> {
    // Atributo
    private T value;

    // Constructor
    public Label(T value) throws IllegalArgumentException{
        validarLabel(value);
    }

    // Metodo Auxiliar 1: Constructor
    private void validarLabel(T value){
        if (value instanceof String || value instanceof Integer) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("El label no es válido: solo pueden ser de tipo String o Integer");
        }
    }


    // Getters
    public T getValue() {
        return value;
    }

    // Setter
    public void setValue(T value) {
        validarLabel(value);
    }

    // Metodos Generales
    // Metodo 1: Clonar el valor del Label
    public Label<T> clonar(){
        return new Label<>(this.value) ;
    }

    // Metodo 2: Definicion de equals para comparar labels.
    @Override
    public boolean equals(Object other) {
        if (this == other){return true;}
        if (other == null || getClass() != other.getClass()){return false;}

        Label<?> otherLabel = (Label<?>) other;

        return value.equals(otherLabel.value); // Este equals es de la clase de Value, no de la clase Label.
    }

    // Metodo de impresion
    @Override
    public String toString() {
        return value.toString();
    }



}
