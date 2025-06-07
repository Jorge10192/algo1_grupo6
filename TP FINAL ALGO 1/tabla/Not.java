package tabla;

public class Not implements Filtro {
    private final Filtro f;
    public Not(Filtro f) { this.f = f; }
    public boolean test(DataFrame df, Label<?> rowLabel) {
        return !f.test(df, rowLabel);
    }
}
