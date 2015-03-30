package ext.systems;

import java.util.HashMap;
import java.util.Map;

public class ParserFactory {

    private static final String TYPE_1 = ".type1";
    private static final String TYPE_2 = ".type2";
    private static final String DOT = ".";

    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";

    private static final String UNSUPPORTED_FILE = "Unsupported file type, %s";

    private static final Map<String, Parser> parsers;

    static {
        parsers = new HashMap<String, Parser>();
        parsers.put(TYPE_1, new ExtSystemOneFileParser(SEMICOLON));
        parsers.put(TYPE_2, new ExtSystemTwoFilesParser(COMMA));
    }

    public static Parser createParser(String file) {
        String ext = getExtension(file);
        if (ext == null) {
            throw new RuntimeException(String.format(UNSUPPORTED_FILE, file));
        }
        Parser parser = parsers.get(ext);
        if (parser == null) {
            throw new RuntimeException(String.format(UNSUPPORTED_FILE, file));
        }
        return parser;
    }

    private static String getExtension(String file) {
        int idx = file.lastIndexOf(DOT);
        return idx == -1 ? null : file.substring(idx);
    }

}
