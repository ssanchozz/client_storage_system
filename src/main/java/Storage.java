import client.entities.Client;
import client.entities.Order;
import client.entities.OrderKey;

import java.io.*;
import java.util.HashSet;
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

    //TODO in our case we don't care about the order and we'll access the store by key
    private HashSet<Client> clients; // todo: можно потом запилить, чтобы клиенты хранились упорядоченно по имени и фамилии
                                     // todo: реализовать Comparable
    public Storage(String storageFileName) {
        clients = new HashSet<Client>();
        this.storageFileName = Objects.requireNonNull(
                storageFileName, "storageFileName can't be null");
    }

    //FIXME: I bet there might be something more to add
    public synchronized void save() throws StorageException {  // перед началом работы
        FileOutputStream file = null;
        ObjectOutputStream out = null;
        try {
            file = new FileOutputStream(storageFileName);
            out = new ObjectOutputStream(file);
            out.writeObject(clients);
        } catch (IOException ioex) {
            throw new StorageException(ioex);
        } finally {
            try {
                if (file != null) file.close();
                if (out != null) out.close();
            } catch (IOException ex) {
                throw new StorageException(ex);
            }
        }
    }

    public synchronized void restore() throws StorageException { // при завершении работы
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(storageFileName);
            in = new ObjectInputStream(file);
            HashSet<Client> clients = (HashSet<Client>) in.readObject();
            this.clients = clients;
        } catch (IOException ioex) {
            throw new StorageException(ioex);
        } catch (ClassNotFoundException cex) {
            throw new StorageException(cex);
        } finally {
            try {
                if (file != null) file.close();
                if (in != null) in.close();
            } catch (IOException ex) {
                throw new StorageException(ex);
            }
        }
    }

    /*public synchronized void restoreDataFromExternalSystem(ExtSystemsReader esi) {
        this.clients = esi.readData();
    }*/

    /**
     * Adds a new client together with its orders
     * @param client
     */
    public synchronized void add(Client client) {
        clients.add(client);
    }
    
    /**
     * Adds an order to an existing client.
     * @param client
     * @param order
     * @throws StorageException If the client does not exist, throws the exception.
     */
    public synchronized void addOrders(Client client, Order order) throws StorageException {
        if (client == null) throw new StorageException();
        client.getOrders().put(new OrderKey(order.getNum(), order.getDate()), order);
    }
    
    /**
     * Adds a list of orders to an existing client.
     * @param client
     * @param orders
     * @throws StorageException If the client does not exist, throws the exception.
     */
    public synchronized void addOrders(Client client, List<Order> orders) throws StorageException {
        if (client == null) throw new StorageException();
        for (Order order : orders)
            client.getOrders().put(new OrderKey(order.getNum(), order.getDate()), order);
    }

    //TODO more logically would be to search by ClientKey
    // else you'll pass a client without orders and return the same + orders
    public synchronized Client find() {
        // завтра утром доделаю
        return null;
    }

    private static boolean checkWithRegExp(String name, String surname, String passport) { //todo: завести эксепшн
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
