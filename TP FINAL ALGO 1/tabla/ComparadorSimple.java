package tabla;

public class ComparadorSimple implements Filtro {
    private final Label<?> columnLabel;
    private final String operador; // "<", ">", "="
    private final Object valor;

    public ComparadorSimple(Label<?> columnLabel, String operador, Object valor) {
        this.columnLabel = columnLabel;
        this.operador = operador;
        this.valor = valor;
    }

    @Override
    public boolean test(DataFrame df, Label<?> rowLabel) {
        Cell<?> celda = df.obtenerCelda(rowLabel, columnLabel);
        Comparable celdaValor = (Comparable) celda.getValue();
        Comparable filtroValor = (Comparable) valor;

        switch (operador) {
            case "<": return celdaValor.compareTo(filtroValor) < 0;
            case ">": return celdaValor.compareTo(filtroValor) > 0;
            case "=": return celdaValor.compareTo(filtroValor) == 0;
            default: throw new IllegalArgumentException("Operador inválido: " + operador);
        }
    }
}