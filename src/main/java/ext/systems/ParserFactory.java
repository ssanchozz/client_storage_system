package ext.systems;

import java.io.File;

public class ParserFactory {

    public Parser createParser(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        if (fileName.contains(".type1")) {
            return new ExtSystemOneFileParser();
        }
        if (fileName.contains(".type2")) {
            return new ExtSystemTwoFilesParser();
        }
        return null;
    }

}
