package tabla;

public class Column {

    //Atributos
    private String name;
    private Class<?> type;

    //Constructor
    public Column(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    //Metodos getter
    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    //Metodos setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    //Mostrar Nombre y tipo de la columna.
    @Override
    public String toString() {
        return name + " (" + type.getSimpleName() + ")";
    }
}
