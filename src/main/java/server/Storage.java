package server;

import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;
import client.entities.OrderKey;
import ext.systems.Parser;
import ext.systems.ParserFactory;

import java.io.*;
import java.util.*;

public class Storage implements Store {

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

            this.clients = (HashMap<ClientKey, Client>) in.readObject();
            ;
        } catch (IOException | ClassNotFoundException ioex) {
            throw new StorageException(ioex);
        }
    }

    /**
     * Adds a new client together with its orders.
     * If client already is in the storage, adds
     * orders to an existing client.
     * @param client
     */
    @Override
    public synchronized void add(Client client) {
        Objects.requireNonNull(client, "client can't be null");
        Client findClient = find(client.getKey());
        if (findClient == null) {
            clients.put(client.getKey(), new Client(client));
        } else {
            addOrders(client.getKey(), client.getOrders().values());
        }
    }

    /**
     * Adds an order to an existing client.
     * If the client with the key doesn't exist, creates
     * a new one with the order.
     * @param key
     * @param order
     */
    @Override
    public synchronized void addOrders(ClientKey key, Order order) {
        List<Order> list = new ArrayList<>();
        list.add(order);
        addOrders(key, list);
    }

    /**
     * Adds a list of orders to an existing client.
     * If the client with the key doesn't exist, creates
     * a new one with the orders.
     * @param key
     * @param orders
     */
    @Override
    public synchronized void addOrders(ClientKey key, Collection<Order> orders) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(orders);

        Client findClient = find(key);
        Map<OrderKey, Order> ordersMap = new HashMap<>();

        if (findClient == null) {
            // create a new client
            findClient = new Client(key, "");
        } else {
            // it exists, just copy its orders
            ordersMap.putAll(findClient.getOrders());
        }

        // now, just add the required orders and set them for the client
        for (Order o : orders) {
            ordersMap.put(o.getKey(), new Order(o));
        }
        findClient.setOrders(ordersMap);

        clients.put(key, findClient);
    }

    @Override
    public synchronized Client find(ClientKey key) {
        Client findClient = clients.get(Objects.requireNonNull(key));
        return findClient == null ? null : new Client(findClient);
    }

    @Override
    public synchronized Client getClient(ClientKey key) {
        Client findClient = clients.get(Objects.requireNonNull(key));
        if (findClient != null) {
            findClient = new Client(findClient);
            clients.remove(key);
        }
        return findClient == null ? null : findClient;
    }

    @Override
    public Iterator<Client> iterator() {
        final Collection<Client> clients =
                Collections.unmodifiableCollection(this.clients.values());
        return clients.iterator();
    }

    /**
     * Reads data from external file, specified by filePath into storage.
     * If external system has two files, you should specify any one.
     * @param filePath
     */
    public synchronized void readExtSystemData(String filePath) {
        Parser parser = ParserFactory.createParser(filePath);
        //TODO: if you continue, we would have no options but to load whatever they sent us
        // may be the storage and the loader will be separate? There's no strict requirements
        // just would like to understand the behaviour.

        // HashMap<ClientKey, Client> extData = parser.getDataFromExtSystem();

    }

    public String toString() {
        return clients.toString();
    }

}
