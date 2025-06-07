package importador;

import tabla.DataFrame;
import java.io.IOException;

import exceptions.CSVParserException;

public interface ImportDataFrame {
    DataFrame importar(String rutaArchivo, boolean hasHeader) throws IOException, CSVParserException;;
}