package importador;
import java.util.List;
import java.util.Arrays;

public class EncodingUtils {
    public static final List<String> encodings = Arrays.asList(
        "UTF-8", "UTF-16", "UTF-16BE", "UTF-16LE",
        "ISO-8859-1", "ISO-8859-2", "ISO-8859-15",
        "US-ASCII", "Windows-1250", "Windows-1251", "Windows-1252",
        "Windows-1253", "Windows-1254", "Windows-1255", "Windows-1256",
        "Windows-1257", "Windows-1258", "MacRoman", "Big5", "GB2312",
        "GB18030", "EUC-JP", "Shift_JIS", "ISO-2022-JP", "EUC-KR", "ISO-2022-KR"
    );
}
