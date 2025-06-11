package tabla;

import com.sun.jdi.InvalidTypeException;
import java.util.ArrayList;
import java.util.List;

public class Column<T> {
    // Atributos
    private Label<T> label;
    private List<Cell<T>> cells;

    // Constructor 1: Columna vacía
    public Column(Label<T> label) {
        this.label = label;
        this.cells = new ArrayList<>();
    }

    // Constructor 2: Columna con contenido
    public Column(Label<T> label, List<Cell<T>> cells) {
        this.label = label;
        this.cells = new ArrayList<>(cells);
    }

    // Getters
    public Label<T> getLabel() {
        return label;
    }

    public List<Cell<T>> getCells() {
        return cells;
    }

    // Setter
    public void setLabel(Label<T> label) {
        this.label = label;
    }

    public void setCell(int i, T value) throws IndexOutOfBoundsException, InvalidTypeException, IllegalStateException {
        validarIndice(i);

        // Permitir que sea de tipo NA
        if (value instanceof MissingValue) {
            cells.get(i).setValue(value);
            return;
        }

        // Comprobar si T coincide con el tipo de la columna
        if (!value.getClass().equals(this.getType())) {
            throw new InvalidTypeException();
        }
        /*
        if (this.getType() == null) {
            throw new IllegalStateException("El tipo de la columna no ha sido definido.");
        }
        */

        cells.get(i).setValue(value);
    }

    // Metodo Getter 3: GETTER DE CELDA
    // Obtener valor de una celda dentro de la columna
    public Cell<T> getCell(int rowIndex) throws IndexOutOfBoundsException {
        validarIndice(rowIndex);
        return cells.get(rowIndex);
    }

    // Validar índice
    private void validarIndice(int i) {
        if (i < 0 || i >= cells.size()) {
            throw new IndexOutOfBoundsException("Índice fuera del rango: " + i);
        }
    }

    // Agregar una celda
    public void addCell(Cell<T> cell) throws InvalidTypeException {
        if (!validarTipo(cell)) {
            throw new InvalidTypeException();
        }
        cells.add(cell);
    }

    // Validar tipo de celda
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

        // Usar getType para determinar el tipo de la columna
        Class<?> tipoColumna = getType();
        if (tipoColumna == null) {
            return true; // No hay tipo definido aún, se acepta cualquier cosa
        }

        // Comparar el tipo del valor con el tipo de la columna
        return value != null && tipoColumna.equals(value.getClass());
    }

    // Agregar celda desde un objeto (con cast seguro)
    @SuppressWarnings("unchecked")
    public void addCellFromObject(Object valor) throws InvalidTypeException {
        // Agregar valores faltantes a la columna
        if (valor instanceof MissingValue) {
            cells.add(new Cell<T>((T) valor));
            return;
        }

        // Verifica que la columna no tiene un tipo definido
        Class<?> tipoColumna = getType();
        if (cells.isEmpty() || tipoColumna == null) {
            cells.add(new Cell<T>((T) valor));
            return;
        }
        // Agrega el valor a la columna si tiene el mismo tipo
        if (valor != null && valor.getClass().equals(tipoColumna)) {
            cells.add(new Cell<T>((T) valor));
        } else {
            throw new InvalidTypeException();
        }
    }

    // Obtener el tipo de la columna
    public Class<?> getType() {
        if (cells.isEmpty()) return null;
        for (Cell<T> c : cells) {
            Object val = c.getValue();
            if (!(val instanceof MissingValue)) {
                return val.getClass();
            }
        }
        return null;
    }

    // Tamaño de la columna
    public int size() {
        return cells.size();
    }
}