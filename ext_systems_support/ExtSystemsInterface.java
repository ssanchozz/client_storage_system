//FIXME: same as for client entities, generally underscores look a bit scary here
package ext_systems_support;

import client_entities.Client;

import java.util.ArrayList;

//TODO: I know, we agreed on system1&2, may be call it ExtSystemsReader/Support?
public interface ExtSystemsInterface {

    public ArrayList<Client> readData();

}
