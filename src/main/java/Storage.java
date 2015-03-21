import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;
import client.entities.OrderKey;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//TODO: ideally it can also implement an interface
public class Storage {

    private String storageFileName;

    private HashMap<ClientKey, Client> clients;
    
    public Storage(String storageFileName) {
        clients = new HashMap<ClientKey, Client>();
        this.storageFileName = Objects.requireNonNull(
                storageFileName, "storageFileName can't be null");
    }

    /**
     * 
     * @throws StorageException
     */
    public synchronized void save() throws StorageException {
        try (ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(storageFileName))) {

            out.writeObject(clients);
        } catch (IOException ioex) {
            throw new StorageException(ioex);
        }
    }

    /**
     * 
     * @throws StorageException
     */
    public synchronized void restore() throws StorageException {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(storageFileName))) {

            this.clients = (HashMap<ClientKey, Client>) in.readObject();;
        } catch (IOException|ClassNotFoundException ioex) {
            throw new StorageException(ioex);
        } 
    }

    /**
     * Adds a new client together with its orders
     * @param client
     */
    public synchronized void add(Client client) {
        Objects.requireNonNull(client, "client can't be null");
        clients.put(client.getKey(), client);
    }
    
    /**
     * Adds an order to an existing client.
     * @param key
     * @param order
     */
    public synchronized void addOrders(ClientKey key, Order order) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(order);
        Client findClient = clients.get(key);
        if (findClient == null) {
            Client newClient = new Client(key, "");
            ArrayList<Order> listOrders = new ArrayList<Order>();
            listOrders.add(order);
            newClient.setOrders(listOrders);
            clients.put(key, newClient);
        } else {
            List<Order> listOrders = findClient.getOrders();
            listOrders.add(order);
            findClient.setOrders(listOrders);
        }
    }
    
    /**
     * Adds a list of orders to an existing client.
     * @param key
     * @param orders
     */
    public synchronized void addOrders(ClientKey key, List<Order> orders) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(orders);
        Client findClient = clients.get(key);
        if (findClient == null) {
            Client newClient = new Client(key, "");
            newClient.setOrders(orders);
            clients.put(key, newClient);
        } else {
            List<Order> listOrders = findClient.getOrders();
            listOrders.addAll(orders);
            findClient.setOrders(listOrders);
        }
    }

    public synchronized Client find(ClientKey key) {
        return new Client(clients.get(Objects.requireNonNull(key)));
    }

}
