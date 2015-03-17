import client.entities.Client;
import ext.systems.ExtSystemsReader;

import java.io.*;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: expand *

//TODO: ideally it can also implement an interface
public class Storage {

    public static final String NAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String SURNAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String PASSPORT_PATTERN = "^[A-Z0-9]{5}$";

    private String storageFileName;

    //private ArrayList<Client> clients;
    private TreeSet<Client> clients; // todo: можно потом запилить, чтобы клиенты хранились упорядоченно по имени и фамилии
                                     // todo: реализовать Comparable
    public Storage(String storageFileName) {
        if (storageFileName == null) throw new IllegalArgumentException();
        clients = new TreeSet<Client>();
        this.storageFileName = storageFileName;
    }

    //FIXME: I bet there might be something more to add
    public synchronized void save() throws IOException {  // перед началом работы
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storageFileName));
        out.writeObject(clients);
    }

    //FIXME: and here too. Throws look a bit scary
    public synchronized void restore() throws IOException, ClassNotFoundException { // при завершении работы  //todo: добавить свой эксепшн
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(storageFileName));
        TreeSet<Client> clients = (TreeSet<Client>)in.readObject();
        this.clients = clients;
    }

    public synchronized void restoreDataFromExternalSystem(ExtSystemsReader esi) {
        this.clients = esi.readData();
    }

    public synchronized void insert(Client client) {
        clients.add(client);
    }

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
