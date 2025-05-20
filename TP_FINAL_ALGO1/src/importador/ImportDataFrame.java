package importador;

import java.io.IOException;
import tabla.DataFrame;
import tabla.HeaderOption;

public interface ImportDataFrame {
    DataFrame importar(String rutaArchivo, HeaderOption headerOption) throws IOException;
}