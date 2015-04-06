package server;

import client.entities.Client;
import client.entities.ClientKey;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class StorageBufferTest {

    private StorageBuffer storageBuffer;
    private Storage storage;

    @Before
    public void setup() {
        storage = new Storage("");
        storageBuffer = new StorageBuffer(10, storage);

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
                for (int i = 0; i < 100; i++) {
                    ClientKey key = new ClientKey("Alex", "Bogd", "AAA55");
                    Thread.sleep(random.nextInt(1500));
                    Client client = sb.get(key);
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
                for (int i = 0; i < 100; i++) {
                    ClientKey key = new ClientKey("Alex", "Bogd", "AAA55");
                    Client client = new Client(key, "");
                    Thread.sleep(random.nextInt(1000));
                    sb.put(client);
                }
            } catch (InterruptedException e) {}
        }

    }
}
