package tabla;

public class Or implements Filtro {
    private final Filtro f1, f2;
    public Or(Filtro f1, Filtro f2) { this.f1 = f1; this.f2 = f2; }
    public boolean test(DataFrame df, Label<?> rowLabel) {
        return f1.test(df, rowLabel) || f2.test(df, rowLabel);
    }
}