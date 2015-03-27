package ext.systems;

import java.io.File;

public class ParserFactory {

    public static Parser createParser(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        //FIXME: are you sure 'contains' will suit the best?
        if (fileName.contains(".type1")) {
            //TODO: private static final
            return new ExtSystemOneFileParser();
        }
        if (fileName.contains(".type2")) {
            return new ExtSystemTwoFilesParser();
        }
        return null;
    }

}
