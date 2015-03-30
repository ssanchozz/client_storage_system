package ext.systems;

import java.io.File;
import java.util.List;

import client.entities.Client;

public class ExtSystemTwoFilesParser implements Parser {

    public static final String CLIENT_FILE_NAME = "client.type2";
    public static final String ORDER_FILE_NAME = "order.type2";

    private final String separator;

    public ExtSystemTwoFilesParser(String separator) {
        this.separator = separator;
    }

    @Override
    public List<Client> parse(File file) {
        // TODO Auto-generated method stub
        return null;
    }

    // TODO: basically the same as for 1 file, but first parse 1 line from 1 file
    // and then look through the 2 file.

}
