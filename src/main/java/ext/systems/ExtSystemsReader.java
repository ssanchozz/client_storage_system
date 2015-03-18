package ext.systems;

import client.entities.Client;

import java.util.TreeSet;

public interface ExtSystemsReader {

    //FIXME: poor child... do I really care about any order especially when reading from an external
    // source?
    public TreeSet<Client> readData();

}
