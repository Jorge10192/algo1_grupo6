package tabla;

import java.util.ArrayList;
import java.util.List;
import exceptions.*;

public class Column<T> {
    //Atributos
    private Label<T> label;
    private List<Cell<T>> cells;

    //Constructores
    public Column(Label<T> label){
        this.label = label;
        this.cells = new ArrayList<>();
    }
    public Column(Label<T> label, List<Cell<T>> cells){
        this.label = label;
        this.cells = cells;
    }

    //Getters

    public Label<T> getLabel() {
        return this.label;
    }
    public Cell<T> getCell(int i){
        return cells.get(i);
    }
    public List<Cell<T>> getCells(){
        return cells;
    }
    public Class<?> getType() {
        //Tener en cuenta el caso de qué pasaría si el primer dato es NA
        if (this.cells.isEmpty()) {
            return null; // o sería mejor lanzar una excepción?
        }
        return this.cells.get(0).getClass();
    }

    //Setters

    public void setLabel(Label<T> label){
        //Tener en cuenta si se agrega un tipo de dato NA
        this.label=label;
    }

    //Metodos


    public void addCell(Cell<T> cell) throws InvalidTypeException{
         
        //tener en cuenta también los datos de tipo NA
        if(!validarTipo(cell)){
            throw new InvalidTypeException();
        }
        //Prueba
        //System.out.println(cell.getValue().toString() +": "+cell.getValue().getClass().toString());
        cells.add(cell);
    }

    public int size(){
        return cells.size();
    }


    private boolean validarTipo(Cell<T> cell) {
        Object value = cell.getValue();

        // Aceptar valores NA sin importar el tipo
        if (value instanceof MissingValue) {
            return true;
        }

        // Si la columna está vacía, aceptar cualquier tipo no NA
        if (cells.isEmpty()) {
            return true;
        }

        Object firstValue = cells.get(0).getValue();

        // Si el primer valor es NA, no se puede usar como referencia para el tipo
        if (firstValue instanceof MissingValue) {
            // Buscar el primer valor no NA como referencia de tipo
            for (Cell<T> c : cells) {
                if (!(c.getValue() instanceof MissingValue)) {
                    firstValue = c.getValue();
                    break;
                }
            }
            // Si no hay valores válidos aún, aceptar cualquier tipo
            if (firstValue instanceof MissingValue) {
                return true;
            }
        }

        return value != null && value.getClass().equals(firstValue.getClass());
    }

}
