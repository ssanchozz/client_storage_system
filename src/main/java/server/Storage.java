package server;

import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;
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

            this.clients = (HashMap<ClientKey, Client>) in.readObject();;
        } catch (IOException|ClassNotFoundException ioex) {
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
            clients.put(client.getKey(), client);
        } else {
            addOrders(client.getKey(), client.getOrders());
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
        Objects.requireNonNull(key);
        Objects.requireNonNull(order);
        Client findClient = clients.get(key);
        List<Order> listOrders;
        if (findClient == null) {
            findClient = new Client(key, "");
            listOrders = new ArrayList<Order>();
            clients.put(key, findClient);
        } else {
            listOrders = findClient.getOrders();
        }
        listOrders.add(order);
        findClient.setOrders(listOrders);
    }
    
    /**
     * Adds a list of orders to an existing client.
     * If the client with the key doesn't exist, creates
     * a new one with the orders.
     * @param key
     * @param orders
     */
    public synchronized void addOrders(ClientKey key, List<Order> orders) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(orders);
        Client findClient = clients.get(key);
        if (findClient == null) {
            findClient = new Client(key, "");
            clients.put(key, findClient);
        } else {
            orders.addAll(findClient.getOrders());
            clients.put(key, findClient);
        }
        findClient.setOrders(orders);
        System.out.println();
    }

    @Override
    public synchronized Client find(ClientKey key) {
        Client findClient = clients.get(Objects.requireNonNull(key));
        return findClient == null ? null : new Client(findClient);
    }
    
    @Override
    public Iterator<Client> iterator() {
        //TODO: Please, would you specify a test that breaks my iterator?
        final Collection<Client> clients =
                Collections.unmodifiableCollection(this.clients.values());
        final Iterator<Client> it = clients.iterator();
        return new Iterator<Client>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Client next() {
                return it.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
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

}
