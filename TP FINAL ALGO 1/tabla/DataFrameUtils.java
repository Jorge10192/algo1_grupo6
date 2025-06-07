package tabla;

import java.util.List;
import java.util.ArrayList;


public class DataFrameUtils {

    //Concatena dos DataFrames con mismo conjunto de columnas.
    /**
     * Concatena dos DataFrames que pueden tener el mismo conjunto de columnas pero en distinto orden.
     * Devuelve un nuevo DataFrame con todas las filas y columnas en el orden del primer DataFrame.
     * Solo verifica que ambos tengan los mismos labels de columna (sin importar el orden).
     */
    public static DataFrame concatenar(DataFrame df1, DataFrame df2) throws Exception {
        // 1. Obtener labels de columnas del primer DataFrame, que será el "orden estándar"
        List<Object> ordenColumnas = new ArrayList<>();
        for (int i = 0; i < df1.contarColumnas(); i++) {
            ordenColumnas.add(df1.obtenerLabelColumnaPorIndice(i));
        }

        // 2. Verificar que df2 tiene los mismos labels de columnas (sin importar el orden)
        List<Object> labelsDf2 = new ArrayList<>();
        for (int i = 0; i < df2.contarColumnas(); i++) {
            labelsDf2.add(df2.obtenerLabelColumnaPorIndice(i));
        }
        if (!labelsDf2.containsAll(ordenColumnas) || !ordenColumnas.containsAll(labelsDf2)) {
            throw new IllegalArgumentException("Ambos DataFrames deben tener exactamente los mismos labels de columna.");
        }

        // 3. Reordenar columnas de df2 al orden de df1
        DataFrame df2Reordenado = reordenarColumnas(df2, ordenColumnas);

        // 4. Concatenar (verificación de orden y tipo solo aquí)
        return concatenarOrdenado(df1, df2Reordenado);
    }

    //Metodo Aux. 1: Reordena las columnas de un DataFrame a partir de una lista de columnLabels.
    /**
     * Devuelve un nuevo DataFrame con las columnas reordenadas según nuevoOrdenLabels.
     * No modifica el DataFrame original.
     */
    public static DataFrame reordenarColumnas(DataFrame df, List<Object> nuevoOrdenLabels)
            throws Exception
    {
        int filas = df.contarFilas();
        int columnas = nuevoOrdenLabels.size();

        // Construccion de lista de listas de datos en el nuevo orden.
        List<List<Object>> datosReordenados = new ArrayList<>(filas);

        for (int i = 0; i < filas; i++) {
            List<Object> nuevaFila = new ArrayList<>(columnas);
            for (int j = 0; j < columnas; j++) {
                Object labelDeseado = nuevoOrdenLabels.get(j);
                Label<?> label = new Label<>(labelDeseado);

                // Encontrar índice de la columna original con ese label
                int idxCol = -1;
                for (int k = 0; k < df.contarColumnas(); k++) {
                    if (df.obtenerLabelColumnaPorIndice(k).equals(label)) {
                        idxCol = k;
                        break;
                    }
                }
                if (idxCol == -1) {
                    throw new IllegalArgumentException("No se encontró la columna con label: " + label);
                }

                List<Cell<?>> fila = df.obtenerFilaPorIndice(i);
                nuevaFila.add(fila.get(idxCol).getValue());
            }
            datosReordenados.add(nuevaFila);
        }

        // Labels de fila originales
        List<Object> rowLabels = new ArrayList<>();
        for (int i = 0; i < filas; i++) {
            rowLabels.add(df.obtenerLabelFilaPorIndice(i));
        }

        return new DataFrame(datosReordenados, nuevoOrdenLabels, rowLabels);
    }

    //Metodo Aux 2: Concatena DataFrames con columnLabels con mismo orden.
    /**
     * Concatena dos DataFrames que ya tienen columnas en el mismo orden y tipo.
     * Conserva los Labels originales de las filas.
     * Solo realiza una verificación estricta de igualdad de labels y cantidad de columnas.
     */
    public static DataFrame concatenarOrdenado(DataFrame df1, DataFrame df2) {
        // Verificación: columnas en igual orden y cantidad
        if (df1.contarColumnas() != df2.contarColumnas()) {
            throw new IllegalArgumentException("Los DataFrames deben tener la misma cantidad de columnas.");
        }
        for (int i = 0; i < df1.contarColumnas(); i++) {
            Label<?> label1 = df1.obtenerLabelColumnaPorIndice(i);
            Label<?> label2 = df2.obtenerLabelColumnaPorIndice(i);
            if (!label1.equals(label2)) {
                throw new IllegalArgumentException("Las etiquetas de columna no coinciden en la posición " + i);
            }
            // (Opcional) Agregar verificación de tipos.
        }

        //Armado de los parametros de constructor de DataFrame para List<List>
        int filas1 = df1.contarFilas();
        int filas2 = df2.contarFilas();
        int columnas = df1.contarColumnas();

        // Armado de datos (data) como List<List<Object>>
        List<List<Object>> datosCombinados = new ArrayList<>(filas1 + filas2);

        // Cargar datos de filas de df1
        for (int i = 0; i < filas1; i++) {
            List<Cell<?>> fila = df1.obtenerFilaPorIndice(i);
            List<Object> nuevaFila = new ArrayList<>(columnas);
            for (int j = 0; j < columnas; j++) {
                nuevaFila.add(fila.get(j).getValue());
            }
            datosCombinados.add(nuevaFila);
        }

        // Cargar datos de filas de df2 seguido de datos de df1
        for (int i = 0; i < filas2; i++) {
            List<Cell<?>> fila = df2.obtenerFilaPorIndice(i);
            List<Object> nuevaFila = new ArrayList<>(columnas);
            for (int j = 0; j < columnas; j++) {
                nuevaFila.add(fila.get(j).getValue());
            }
            datosCombinados.add(nuevaFila);
        }

        // Labels de columnas (las del primero)
        List<Object> columnLabels = new ArrayList<>();
        for (int i = 0; i < columnas; i++) {
            columnLabels.add(df1.obtenerLabelColumnaPorIndice(i));
        }

        // Labels de fila: concatenados
        List<Object> rowLabels = new ArrayList<>();
        for (int i = 0; i < filas1; i++) {
            rowLabels.add(df1.obtenerLabelFilaPorIndice(i));
        }
        for (int i = 0; i < filas2; i++) {
            rowLabels.add(df2.obtenerLabelFilaPorIndice(i));
        }

        try {
            return new DataFrame(datosCombinados, columnLabels, rowLabels);
        } catch (Exception e) {
            throw new RuntimeException("Error al concatenar los DataFrames: " + e.getMessage(), e);
        }
    }



}