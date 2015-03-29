package ext.systems;

import client.entities.Client;
import client.entities.ClientKey;

import java.io.File;
import java.util.Map;

public interface Parser {

    // If you meant 'setDirectory', I would understand.
    // this one won't work in a multithreaded environment.
    public void setFile(File file);

    public Map<ClientKey, Client> processFile();

    public Client parse(String line);

}
