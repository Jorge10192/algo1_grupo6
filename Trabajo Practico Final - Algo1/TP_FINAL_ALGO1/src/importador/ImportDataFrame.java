package importador;

import java.io.IOException;
import tabla.DataFrame;

public interface ImportDataFrame {
    DataFrame importar(String rutaArchivo) throws IOException;
}