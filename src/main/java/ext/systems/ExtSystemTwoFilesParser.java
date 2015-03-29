package ext.systems;

import client.entities.Client;
import client.entities.ClientKey;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public class ExtSystemTwoFilesParser implements Parser {

    public static final String CLIENT_FILE_NAME = "client.type2";
    public static final String ORDER_FILE_NAME = "order.type2";

    File fileToParseClient;
    File fileToParseOrder;

    @Override
    public void setFile(File file) {
        Objects.requireNonNull(file);
        String path = file.getPath();
        
        // TODO: I would use just 1 hash map. this implementation
        // is a bit frightening and won't work with multiple threads.
        
        if (CLIENT_FILE_NAME.equals(file.getName())) {
            path = path.substring(0, path.length() - CLIENT_FILE_NAME.length());
            fileToParseClient = file;
            fileToParseOrder = new File(path + ORDER_FILE_NAME);
        } else {
            path = path.substring(0, path.length() - ORDER_FILE_NAME.length());
            fileToParseOrder = file;
            fileToParseOrder = new File(path + CLIENT_FILE_NAME);
        }
    }

    @Override
    public Map<ClientKey, Client> processFile() {
        return null;
    }

    @Override
    public Client parse(String line) {
        return null;
    }

}
