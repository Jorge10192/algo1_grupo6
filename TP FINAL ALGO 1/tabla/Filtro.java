package tabla;

public interface Filtro {
    boolean test(DataFrame df, Label<?> rowLabel);
}
