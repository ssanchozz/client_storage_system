package server;
import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;
import ext.systems.OneFileParserCreator;
import ext.systems.Parser;
import ext.systems.ParserCreator;
import ext.systems.TwoFilesParserCreator;

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
        // TODO Auto-generated method stub
        return null;
    }

    public synchronized void readExtSystemData(String filePath) {
        File file = new File(filePath);
        Parser parser;
        ParserCreator creator;
        String fileName = file.getName();
        
        // sounds a bit frightening, check the unit tests
        // what if I'd like to use the same but in another place?
        if (fileName.contains(".type1")) {
            creator = new OneFileParserCreator();
            parser = creator.factoryMethod(file);
        }
        if (fileName.contains(".type2")) {
            creator = new TwoFilesParserCreator();
            parser = creator.factoryMethod(file);
        }
        
        // I would create 1 interface, 2 parsers and 1 factory.

        // HashMap<ClientKey, Client> extData = parser.getDataFromExtSystem();

    }

}
