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
     * Adds a new client together with its orders
     * @param client
     */
    @Override
    public synchronized void add(Client client) {
        //TODO: please specify behaviour of the method. The implementation can be done in 2 
        // different ways. what if the client already exists? what is the behaviour?
        // this needs to be documented
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
            //FIXME: javadoc says about an existing client...
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
     * @param key
     * @param orders
     */
    public synchronized void addOrders(ClientKey key, List<Order> orders) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(orders);
        Client findClient = clients.get(key);
        if (findClient == null) {
            //TODO same as above. I don't mind but the behaviour must be documented
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
        //FIXME: Wonderful. How about unmodifiable collections?
        final Collection<Client> clients = this.clients.values();
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

    public synchronized void readExtSystemData(String filePath) {
        Parser parser = ParserFactory.createParser(filePath);
        //TODO: if you continue, we would have no options but to load whatever they sent us
        // may be the storage and the loader will be separate? There's no strict requirements
        // just would like to understand the behaviour.

        
        // I would create 1 interface, 2 parsers and 1 factory.

        // HashMap<ClientKey, Client> extData = parser.getDataFromExtSystem();

    }

}
