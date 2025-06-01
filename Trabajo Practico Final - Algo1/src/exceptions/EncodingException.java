package exceptions;

import importador.EncodingUtils;

public class EncodingException extends RuntimeException {
    private final String encoding;

    public EncodingException(String encoding){
        super();  // Llama al constructor de Exception (opcional si no hay mensaje)
        this.encoding = encoding;
    }

    @Override
    public String getMessage(){
        String mensaje = "El encoding utilizado: " + encoding + " no es válido. Utilice alguno de los siguientes: \n";
        mensaje += EncodingUtils.encodings.toString();
        return mensaje;
    }
}

