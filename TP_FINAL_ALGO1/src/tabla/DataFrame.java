package tabla;

import java.util.HashSet;
import java.util.Set;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import importador.ImportCSV;


import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

public class DataFrame {

    //Atributos
    private final List<Column> columns;
    private final List<Row> rows;

    //Se utiliza solo en caso de que la tabla no tenga indices
    private boolean tieneIndices;
    private int nextIndex = 0; // para índices automaticos

    //Constructores
    // Constructor Basico sin datos (vacio)
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.tieneIndices = false;
    }

    //Constructor con datos agregados
    //Constructor1 para Lista<Lista>
    public DataFrame(List<List<Object>> datos, HeaderOption headerOption, IndexOption indexOption) {
        this();
        this.tieneIndices = (indexOption == IndexOption.WITH_INDEX);

        if (datos == null || datos.isEmpty()) return;

        int filaInicial = 0;

        // Cargar columnas
        if (headerOption == HeaderOption.WITH_HEADERS) {
            cargarColumnas(datos.get(0));
            filaInicial = 1;
        } else {
            crearColumnasGenericas(datos.get(0).size());
        }

        // Si no tiene índice, agrego columna índice
        if (!tieneIndices) {
            agregarColumnaIndice();
        }

        // Cargar filas
        cargarFilas(datos, filaInicial);
    }

    // Constructor2 con matriz bidimensional
    public DataFrame(Object[][] datos, HeaderOption headerOption, IndexOption indexOption) {
        this(convertirMatrizALista(datos), headerOption, indexOption);
    }

    //Constructor3 cargar datos desde CSV
    public DataFrame(String rutaCSV, HeaderOption headerOption, IndexOption indexOption) throws IOException {
        ImportCSV importador = new ImportCSV(indexOption);
        DataFrame dfImportado = importador.importar(rutaCSV, headerOption);

        // Copiar columnas y filas importadas al this
        this.columns.clear();
        this.columns.addAll(dfImportado.getColumns());

        this.rows.clear();
        this.rows.addAll(dfImportado.getRows());

        this.tieneIndices = dfImportado.tieneIndices;
        this.nextIndex = dfImportado.nextIndex;
    }

    //Metodo adicional para convertir Matriz a Lista<Lista> en Constructor2
    private static List<List<Object>> convertirMatrizALista(Object[][] matriz) {
        List<List<Object>> lista = new ArrayList<>();
        for (Object[] fila : matriz) {
            List<Object> filaLista = new ArrayList<>();
            for (Object celda : fila) {
                filaLista.add(celda);
            }
            lista.add(filaLista);
        }
        return lista;
    }



    //Metodos vacios para cargar Datos.
    //Metodos para cargar Columnas
    //Cargar columnas para DataFrames con encabezados (With_Header = True)
    private void cargarColumnas(List<Object> filaEncabezado) {
        //Defino conjunto de nombres de columnas validos para no repetirlos.
        Set<String> nombresExistentes = new HashSet<>();
        int contadorAuto = 0;
        Map<String, Integer> contadorSufijos = new HashMap<>();

        for (int i = 0; i < filaEncabezado.size(); i++) {
            Object nombre = filaEncabezado.get(i);
            String nombreInicial;

            //Determinar el nombre de la columna si es vacio o null
            if (nombre == null || nombre.toString().trim().isEmpty()) {
                nombreInicial = "AutoColumn" + contadorAuto++;
            } else {
                nombreInicial = nombre.toString().trim();
            }

            String nombreFinal = nombreInicial;

            // Resolver duplicados
            if (nombresExistentes.contains(nombreFinal)) {
                int sufijo = contadorSufijos.getOrDefault(nombreInicial, 1);
                do {
                    nombreFinal = nombreInicial + "_" + sufijo++;
                } while (nombresExistentes.contains(nombreFinal));
                contadorSufijos.put(nombreInicial, sufijo);
            }
            //Añade nombre de la columna
            nombresExistentes.add(nombreFinal);
            columns.add(new Column(nombreFinal, Object.class));
        }
    }



    //Cargar columnas para DataFrames sin encabezados (With_Header = False)
    private void crearColumnasGenericas(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            String nombre = "AutoColumn" + i;
            columns.add(new Column(nombre, Object.class));
        }
    }

    //Agrega la columna índice al inicio de la lista de columnas (With_Index = False)
    private void agregarColumnaIndice() {
        Column columnaIndice = new Column("Indice", Integer.class);
        columns.add(0, columnaIndice);
    }

    //Metodo para cargar filas
    //El entero filaInicial indica a partir de donde se cuentas las filas (Posicion 1 si tenia encabezado o posicion 0 sino)
    private void cargarFilas(List<List<Object>> datos, int filaInicial) {
        for (int i = filaInicial; i < datos.size(); i++) {
            List<Object> filaDatos = datos.get(i);

            if (tieneIndices) {
                // Índice en la primera columna (posición 0)
                Row fila = new Row(filaDatos, 0);
                rows.add(fila);

            } else {
                // Generar índice automático
                List<Cell> celdas = new ArrayList<>();
                for (Object valorCelda : filaDatos) {
                    Class<?> tipo = (valorCelda == null) ? Object.class : valorCelda.getClass();
                    celdas.add(new Cell(valorCelda, tipo));
                }
                Row fila = new Row(celdas);
                fila.setIndex(String.valueOf(nextIndex++));
                rows.add(fila);
            }
        }
    }




    // Getters
    public List<Column> getColumns() {
        return columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    public Column getColumnByName(String name) {
        for (Column column : columns) {
            if (column.getName().equals(name)) {
                return column;
            }
        }
        return null;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Mostrar nombres de columnas
        for (Column col : columns) {
            sb.append(col.getName()).append("\t");
        }
        sb.append("\n");

        // Mostrar cada fila
        for (Row row : rows) {
            for (Cell cell : row.getCells()) {
                sb.append(cell.getValue()).append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}