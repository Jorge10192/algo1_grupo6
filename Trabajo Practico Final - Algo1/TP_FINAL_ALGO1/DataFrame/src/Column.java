import java.util.List;
import java.util.ArrayList;

public class Column {
    //Constructor
    public Column() {

    }

    //Atributos
    private String name;
    private String type;
    private int index;
    private List<Cell<?>> cells;
    

    //Metodos
    //Devolver nombre
    public String getName(){
        return name;
    }

    //Metodo getter
    public String getType(){
        return type;
    }

    //Devolver indice de la columna
    public int getIndex(){
        return index;
    }

    /*Tamano de Columna
    public int size(){
        return cells.size();
    }*/

    //Devolver celda de indice de columna
    public Cell<?> getCell(int index){
        if (index >= 0 && index <= cells.size()){
            return cells.get(index);
        } else {
            throw new IndexOutOfBoundsException("Indice fuera de rango.");
        }
    }

}
