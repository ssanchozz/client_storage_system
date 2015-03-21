import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
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
     * @param client
     * @param order
     */
    public synchronized void addOrders(Client client, Order order) {
        Objects.requireNonNull(client);
        // I get copy of stored orders from client as List
        List<Order> storedOrders = client.getOrders();
        // I add new order to List
        storedOrders.add(order);
        // Finally I set List with added order to the client
        // setOrders(List<Order> source) put OrderKey and Order in order's Map
        client.setOrders(storedOrders);
        //TODO: where are you adding data???
        // compare this method and add(Client client)
        // What are you talking about?
    }
    
    /**
     * Adds a list of orders to an existing client.
     * @param client
     * @param orders
     */
    public synchronized void addOrders(Client client, List<Order> orders) {
        Objects.requireNonNull(client);
        List<Order> storedOrders = client.getOrders();
        storedOrders.addAll(orders);
        client.setOrders(storedOrders); //TODO: Same here
    }

    public synchronized Client find(ClientKey key) {
        return new Client(clients.get(Objects.requireNonNull(key)));
    }

}
