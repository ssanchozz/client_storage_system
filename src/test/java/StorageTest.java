import client.entities.ClientKey;
//import org.junit.Test;

import client.entities.Client;

public class StorageTest {

//    @Test
    public void patternsCheck() {
        ClientKey ck1 = new ClientKey("Alexander", "Bogdanov", "123AB");
        Client client1 = new Client(ck1, "client1");

        ClientKey ck2 = new ClientKey("Vsevolod", "Sayapin", "456VS");
        Client client2 = new Client(ck2, "client2");

        ClientKey ck3 = new ClientKey("Anton", "Pushkarev", "789AP");
        Client client3 = new Client(ck3, "client3");

        /*
        Client client4 = new Client("Anton", "Pushkarev", "789ap");
        Client client5 = new Client("Vsevolodddddddddddddd", "Sayapin", "456VS");
        Client client6 = new Client("Alexander", "Bogdanovvvvvvvvvvvvvv", "123AB");
        Client client7 = new Client("Alexander", "", "123AB");
        */
    }

/*
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

    @Test
    public void hackClientOrdersCheck() {
        Client client1 = new Client("Alexander", "Bogdanov", "123AB");
        Order order1 = new Order("1", "2015", "order");
        LinkedHashMap<String, Order> orders = new LinkedHashMap<String, Order>();
        orders.put("1", order1);
        client1.setOrders(orders);
        client1.getOrders().get("1").setNum("2");
        client1.setName("Aleksandr");
        System.out.println(client1.getOrders().get("1").getNum());
        System.out.println(client1.getName());
    }
*/
    // Нужно написать тесты для многопоточного чтения/записи

}
