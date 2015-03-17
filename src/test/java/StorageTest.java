import org.junit.Test;

import client.entities.Client;
import client.entities.Order;
import ext.systems.ExtSystemOneFileAdapter;
import ext.systems.ExtSystemTwoFilesAdapter;

public class StorageTest {

    @Test
    public void patternsCheck() {
        Client client1 = new Client("Alexander", "Bogdanov", "123AB");
        Client client2 = new Client("Vsevolod", "Sayapin", "456VS");
        Client client3 = new Client("Anton", "Pushkarev", "789AP"); // I'm pleased ;-)

        Client client4 = new Client("Anton", "Pushkarev", "789ap");
        Client client5 = new Client("Vsevolodddddddddddddd", "Sayapin", "456VS");
        Client client6 = new Client("Alexander", "Bogdanovvvvvvvvvvvvvv", "123AB");
        Client client7 = new Client("Alexander", "", "123AB");
    }

    @Test
    public void saveRestoreCheck() throws Exception {
        Client client1 = new Client("Alexander", "Bogdanov", "123AB");
        Client client2 = new Client("Vsevolod", "Sayapin", "456VS");
        Client client3 = new Client("Anton", "Pushkarev", "789AP");

        Order order1 = new Order("1", "2015", "order");
        client2.addOrder(order1);

        Storage s = new Storage();
        s.add(client1);
        s.add(client2);
        s.add(client3);
        s.save();
        s.restore();
    }

    @Test
    public void extSystemsReadingCheck() {
        Storage s = new Storage();
        s.restoreDataFromExternalSystem(new ExtSystemOneFileAdapter());
        s.restoreDataFromExternalSystem(new ExtSystemTwoFilesAdapter());
    }

    // Нужно написать тесты для многопоточного чтения/записи

}
