package ext.systems;

import java.io.File;

public class TwoFilesParserCreator extends ParserCreator {

    @Override
    //FIXME: what??? what does it do? I case of 1000 calls you'll
    // end up with 1000 parser instances
    public Parser factoryMethod(File file) {
        Parser parser = new ExtSystemTwoFilesParser();
        parser.setFile(file);
        return parser;
    }

}
