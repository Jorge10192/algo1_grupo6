package importador;

import tabla.DataFrame;
import java.io.IOException;

public interface ImportDataFrame {
    DataFrame importar(String rutaArchivo, boolean hasHeader) throws IOException, CSVParserException;;
}