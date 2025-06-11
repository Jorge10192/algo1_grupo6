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

    // Constructor copia con copia profunda
    public Column(Column<T> other) {
        this.label = new Label<>(other.label); 
        this.cells = new ArrayList<>();
        for (Cell<T> cell : other.cells) {
            this.cells.add(new Cell<>(cell)); 
        }
    }

    //Getters

    public Label<T> getLabel() {
        return this.label;
    }
    public Cell<T> getCell(int i) throws IndexOutOfBoundsException{
        validarIndice(i);
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

        Object firstValue = cells.get(0).getValue();

        // Si el primer valor es NA, no se puede usar como referencia para el tipo
        if (firstValue instanceof MissingValue) {
            // Buscar el primer valor no NA como referencia de tipo
            for (Cell<T> c : cells) {
                if (!(c.getValue() instanceof MissingValue)) {
                    return c.getValue().getClass();
                }
            }
            return null;
            }

        return firstValue.getClass();
    }

    //Setters

    public void setLabel(Label<T> label){
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

    public void setCell(int i, T value) throws IndexOutOfBoundsException, InvalidTypeException, IllegalStateException{

        //Comprobar que i esté dentro del tamaño de la lista
        validarIndice(i);

        //Permitir que sea de tipo NA
        if(value instanceof MissingValue){
            cells.get(i).setValue(value);
            return;
        }
        //Comprobar si T coincide con el tipo de la columna
        if (!value.getClass().equals(this.getType())){
            throw new InvalidTypeException();
        }
        if (this.getType() == null) {
            throw new IllegalStateException("El tipo de la columna no ha sido definido.");
        }
        cells.get(i).setValue(value);
    }

    public int countNA(){
        int i=0;
        for (Cell c:cells){
            if (c.getValue() instanceof MissingValue){
                i++;
            }
        }
        return i;
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
    private void validarIndice(int i){
        if (i < 0 || i >= cells.size()) {
            throw new IndexOutOfBoundsException("Índice fuera del rango: " + i);
    }
    }

    
    public boolean matches(Object obj) {
        if (this == obj) return true; // Son la misma referencia
        if (obj == null || getClass() != obj.getClass()) return false;

        Column<?> other = (Column<?>) obj;

        // Comparo las etiquetas
        if (this.label == null) {
            if (other.label != null) return false;
        } else if (!this.label.equals(other.label)) {
            return false;
        }

        // Comparo tipo de datos
        Class<?> thisType = this.getType();
        Class<?> otherType = other.getType();

        if (thisType == null) {
            return otherType == null;
        }

        return thisType.equals(otherType);
    }

}
