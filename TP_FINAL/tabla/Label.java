package tabla;


public class Label <T>{
    //Atributos
    private T value;

    //Constructor
    public Label (T v) throws IllegalArgumentException{
        validarLabel(v);
    }

    //Getter
    public T getLabel(){
        return value;
    }

    //Setter
    public void setLabel(T v)throws IllegalArgumentException{
        validarLabel(v);
    }

    //Metodo 1: Valida que el label sea String o Integer
    private void validarLabel(T v){
        if(v instanceof String || v instanceof Integer ){
            this.value = v;
        }else{
            throw new IllegalArgumentException("El label no es válido: solo pueden ser de tipo String o Integer");
        }
    }

    //Metodo 2: Impresion de Label
    @Override
    public String toString(){
        return value.toString();
    }

}
