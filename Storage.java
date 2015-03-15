import client_entities.Client;
import ext_systems_support.ExtSystemsInterface;

import java.io.*;
import java.util.ArrayList;

public class Storage {

    private ArrayList<Client> clients; // думаю мапу использовать вместо листа

    public Storage() {
        clients = new ArrayList<Client>();
    }

    public synchronized void save() throws IOException {  // перед началом работы
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("storage.out"));
        out.writeObject(clients);
    }

    public synchronized void restore() throws IOException, ClassNotFoundException { // при завершении работы
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("storage.out"));
        ArrayList<Client> clients = (ArrayList<Client>)in.readObject();
        this.clients = clients;
    }

    public synchronized ArrayList<Client> getDataFromExternalSystem(ExtSystemsInterface esi) {
        return esi.readData();
    }

    public synchronized void insert(Client client) {
        clients.add(client);
    }

    public synchronized Client get(int index) { // Скорее всего не нужен, т.к. непонятно,
        return clients.get(index);              // что будем считывать, если другие потоки будут писать
    }

    public synchronized Client find() {

        return null;
    }

}
