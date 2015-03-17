import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.entities.Client;
import client.entities.Order;
import ext.systems.ExtSystemsReader;

//TODO: ideally it can also implement an interface
public class Storage {

    public static final String NAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String SURNAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String PASSPORT_PATTERN = "^[A-Z0-9]{5}$";

    private String storageFileName;

    //TODO in our case we don't care about the order and we'll access the store by key
    private TreeSet<Client> clients; // todo: можно потом запилить, чтобы клиенты хранились упорядоченно по имени и фамилии
                                     // todo: реализовать Comparable
    public Storage(String storageFileName) {
        clients = new TreeSet<Client>();
        this.storageFileName = Objects.requireNonNull(
                storageFileName, "storageFileName can't be null");;
    }

    //FIXME: I bet there might be something more to add
    public synchronized void save() throws IOException {  // перед началом работы
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storageFileName));
        out.writeObject(clients);
    }

    //FIXME: and here too. Throws look a bit scary. What about throws StorageException?
    public synchronized void restore() throws IOException, ClassNotFoundException { // при завершении работы  //todo: добавить свой эксепшн
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(storageFileName));
        TreeSet<Client> clients = (TreeSet<Client>)in.readObject();
        this.clients = clients;
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
    public synchronized void addOrders(Client client, Order order) throws StorageException{
        
    }
    
    /**
     * Adds a list of orders to an existing client.
     * @param client
     * @param orders
     * @throws StorageException If the client does not exist, throws the exception.
     */
    public synchronized void addOrders(Client client, List<Order> orders) throws StorageException{
        
    }

    //TODO more logically would be to search by ClientKey
    // else you'll pass a client without orders and return the same + orders
    public synchronized Client find() {

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
