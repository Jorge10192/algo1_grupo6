package importador;

import tabla.DataFrame;
import java.io.IOException;

import exceptions.CSVParserException;

public interface ImportDataFrame {
    DataFrame importar(String rutaArchivo, String separator, String encoding, boolean hasHeader) throws IOException, CSVParserException;
}