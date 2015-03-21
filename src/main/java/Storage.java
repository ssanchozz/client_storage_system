import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: ideally it can also implement an interface
public class Storage {

    public static final String NAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String SURNAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String PASSPORT_PATTERN = "^[A-Z0-9]{5}$";

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
        //FIXME: NPE!!!
        clients.put(client.getKey(), Objects.requireNonNull(client, "client can't be null"));
    }
    
    /**
     * Adds an order to an existing client.
     * @param client
     * @param order
     * @throws StorageException If the client does not exist, throws the exception.
     */
    public synchronized void addOrders(Client client, Order order) throws StorageException {
        if (client == null) {
            //TODO: add the reason for the exception,e.g. 'client is null'
            // also for such cases NPE is usually throws, also Objects.requireNotNull()
            throw new StorageException();
        }
        List<Order> storedOrders = client.getOrders();
        storedOrders.add(order);
        client.setOrders(storedOrders); //TODO: where are you adding data??? 
        // compare this method and add(Client client)
    }
    
    /**
     * Adds a list of orders to an existing client.
     * @param client
     * @param orders
     * @throws StorageException If the client does not exist, throws the exception.
     */
    public synchronized void addOrders(Client client, List<Order> orders) throws StorageException {
        if (client == null) {
            throw new StorageException();
        }
        List<Order> storedOrders = client.getOrders();
        storedOrders.addAll(orders);
        client.setOrders(storedOrders); //TODO: Same here
    }

    public synchronized Client find(ClientKey key) {
        //FIXME: this can indirectly modify clients, is that ok?
        return clients.get(Objects.requireNonNull(key));
    }

    //FIXME: move to a separate class and split in methods
    // ideally I'd like to check one field at a time
    private static boolean checkClientKeyWithRegExp(String name, String surname, String passport) { //todo: завести эксепшн
        Pattern namePattern = Pattern.compile(NAME_PATTERN);
        Pattern SurnamePattern = Pattern.compile(SURNAME_PATTERN);
        Pattern passportPattern = Pattern.compile(PASSPORT_PATTERN);

        Matcher nameMatcher = namePattern.matcher(name);
        Matcher surnameMatcher = SurnamePattern.matcher(surname);
        Matcher passportMatcher = passportPattern.matcher(passport);

        return nameMatcher.matches()
                && surnameMatcher.matches()
                && passportMatcher.matches();
    }

}
