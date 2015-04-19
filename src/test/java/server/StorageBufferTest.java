package server;

import client.entities.Client;
import client.entities.ClientKey;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StorageBufferTest {

    private StorageBuffer storageBuffer;
    private Storage storage;
    private List<ClientKey> keys;

    @Before
    public void setup() {
        storage = new Storage("");
        storageBuffer = new StorageBuffer(10, storage);
        keys = generateClientsKeys();
    }

    @Test
    public void testBuffer() {
        System.out.println("test started");
        (new Thread(new StoragePutter(storageBuffer))).start();
        (new Thread(new StorageGetter(storageBuffer))).start();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<ClientKey> generateClientsKeys() {
        Random r = new Random();
        List<ClientKey> keys = new ArrayList<ClientKey>();
        for (int i=0; i < 10; i++) {
            keys.add(new ClientKey("Alex", "Bogd", ("AAA"+r.nextInt(10))+r.nextInt(10)));
        }
        return keys;
    }

    class StorageGetter implements Runnable {

        StorageBuffer sb;

        StorageGetter(StorageBuffer sb) {
            this.sb = sb;
        }

        @Override
        public void run() {
            System.out.println("StorageGetter started!");
            Random random = new Random();
            try {
                for (; true;) {
                    Thread.sleep(random.nextInt(1500));
                    Client client = sb.get(keys.get(random.nextInt(10)));
                    System.out.println(sb);
                }
            } catch (InterruptedException e) {}
        }

    }

    class StoragePutter implements Runnable {

        StorageBuffer sb;

        StoragePutter(StorageBuffer sb) {
            this.sb = sb;
        }

        @Override
        public void run() {
            System.out.println("StoragePutter started!");
            Random random = new Random();
            try {
                for (; true;) {
                    ClientKey key = keys.get(random.nextInt(10));
                    Client foundClient = storage.find(key);
                    if (foundClient != null) {
                        continue;
                    }
                    Client client = new Client(key, "");
                    Thread.sleep(random.nextInt(1000));
                    sb.put(client);
                    System.out.println(sb);
                }
            } catch (InterruptedException e) {}
        }

    }
}
