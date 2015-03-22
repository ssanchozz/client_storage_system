package ext.systems;

import java.io.File;

public class TwoFilesParserCreator extends ParserCreator {

    @Override
    public Parser factoryMethod(File file) {
        Parser parser = new ExtSystemTwoFilesParser();
        parser.setFile(file);
        return parser;
    }

}
