package ext.systems;

import java.io.File;

public class OneFileParserCreator extends ParserCreator {

    @Override
    public Parser factoryMethod(File file) {
        Parser parser = new ExtSystemOneFileParser();
        parser.setFile(file);
        return parser;
    }

}
