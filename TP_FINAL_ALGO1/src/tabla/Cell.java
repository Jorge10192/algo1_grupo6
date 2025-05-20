package tabla;

public class Cell {
    //Atributos
    private Object value;
    private Class<?> type;

    //Constructor
    public Cell(Object value, Class<?> type) {
        this.value = value;
        this.type = type;
    }

    //Metodos getters
    public Object getValue() {
        return value;
    }

    public Class<?> getType() {
        return type;
    }

    //Metodos setters
    public void setValue(Object value) {
        this.value = value;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    //Verificar si una celda tiene contenido nulo.
    public boolean isNull() {
        return this.value == null;
    }

    //Mostrar contenido real no referencia a memoria
    @Override
    public String toString() {
        return value.toString();
    }
}