package server;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;
import ext.systems.OneFileParserCreator;
import ext.systems.Parser;
import ext.systems.ParserCreator;
import ext.systems.TwoFilesParserCreator;

//TODO: ideally it can also implement an interface
public class Storage implements Store{

    public static final int FIRST_EXT_SYSTEM_TYPE = 1;

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
        clients.put(client.getKey(), client);
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
        
        //TODO: how would you think, may we make use of a single listOrder.add()?
        // if you notice, there are some lines duplicated and they have the same logic.
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
        
        // TODO: same as above
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

    @Override
    public synchronized Client find(ClientKey key) {
        return new Client(clients.get(Objects.requireNonNull(key)));
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
        if (fileName.contains(".type1")) {
            creator = new OneFileParserCreator();
            parser = creator.factoryMethod(file);
        }
        if (fileName.contains(".type2")) {
            creator = new TwoFilesParserCreator();
            parser = creator.factoryMethod(file);
        }

        // HashMap<ClientKey, Client> extData = parser.getDataFromExtSystem();

    }

}
