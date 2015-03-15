import client_entities.Client;
import ext_systems_support.ExtSystemsInterface;

//TODO: expand *
import java.io.*;
import java.util.ArrayList;

//TODO: ideally it can also implement an interface
public class Storage {

    private ArrayList<Client> clients; // думаю мапу использовать вместо листа

    public Storage() {
        clients = new ArrayList<Client>();
    }

    //FIXME: I bet there might be something more to add
    public synchronized void save() throws IOException {  // перед началом работы
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("storage.out"));
        out.writeObject(clients);
    }

    //FIXME: and here too. Throws look a bit scary
    public synchronized void restore() throws IOException, ClassNotFoundException { // при завершении работы
        //FIXME I was told 'storage.out' was an input file for another system, you know what to do, eh?
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("storage.out"));
        ArrayList<Client> clients = (ArrayList<Client>)in.readObject();
        this.clients = clients;
    }

    //FIXME: hmmm... I would think of Storage & Parser, also it may ready data to nowhere unless they are saved
    public synchronized ArrayList<Client> getDataFromExternalSystem(ExtSystemsInterface esi) {
        return esi.readData();
    }

    public synchronized void insert(Client client) {
        //FIXME: let's add the same client a couple of times
        clients.add(client);
    }

    public synchronized Client get(int index) { // Скорее всего не нужен, т.к. непонятно,
        return clients.get(index);              // что будем считывать, если другие потоки будут писать
        //TODO: why get by index?, your comment sounds a bit scary to me ;-)
    }

    public synchronized Client find() {

        return null;
    }

}
