package ext.systems;

import java.io.File;
import java.util.List;

import client.entities.Client;

public interface Parser {

    public List<Client> parse(File file);

}
