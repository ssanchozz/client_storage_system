package server;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;
import client.entities.OrderKey;

public class StorageTest {

    private Storage storage;
    
    @Before
    public void setup(){
        storage = new Storage("");
    }
    
    @Test
    //TODO: here comes the oops!
    public void testFind(){
        storage.find(new ClientKey("q", "q", "10QW4"));
    }
    
    @Test
    //TODO: what the heck is that? Oops #2
    public void testAdd(){
        // added a client
        ClientKey key = new ClientKey("q", "q", "10QW4");
        Map<OrderKey, Order> orders = new HashMap<>();
        OrderKey orderKey = new OrderKey("52", "2014-05-05");
        Order order  = new Order(orderKey, "very important order");
        orders.put(orderKey, order);
        Client client = new Client(key, "", orders);
        
        // saved the client
        storage.add(client);
        Client retrievedClient = storage.find(key);
        Assert.assertNotNull(retrievedClient);
        Assert.assertEquals(client, retrievedClient);
        Assert.assertEquals(client.getOrders(), retrievedClient.getOrders());
        
        // now, 1000 lines later, data read from a file
        ClientKey key1 = new ClientKey("q", "q", "10QW4");
        Map<OrderKey, Order> orders1 = new HashMap<>();
        OrderKey orderKey1 = new OrderKey("53", "2014-05-06");
        Order order1  = new Order(orderKey, "even more important order");
        orders1.put(orderKey1, order1);
        Client client1 = new Client(key1, "", orders1);
        
     // saved the client
        storage.add(client1);
        Client retrievedClient1 = storage.find(key);
        Assert.assertNotNull(retrievedClient1);
        //it must be the same client
        Assert.assertEquals(client, retrievedClient1);
        
        Map<OrderKey, Order> expectedOrders = new HashMap<>();
        expectedOrders.put(orderKey, order);
        expectedOrders.put(orderKey1, order1);
        
        // we now see 2 orders
        Assert.assertEquals(expectedOrders, retrievedClient.getOrders());
        
    }
    
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
