package server;

import client.entities.Client;
import client.entities.ClientKey;

import java.util.Objects;

/**
 * Provides a buffer with the limited capacity to store clients and their details.
 * Due to its nature, the buffer will block the current thread if more than the configured
 * amount of clients is attempted to put and vice versa, if there are no
 * clients available, the current thread will be blocked.
 * 
 * @author anton
 *
 */
public class StorageBuffer {

    private Store store;
    private int bufferLimit;
    private int counter = 0;
    private boolean empty, full;

    public StorageBuffer(int bufferLimit, Store store) {
        this.store = Objects.requireNonNull(store);
        this.bufferLimit = Objects.requireNonNull(bufferLimit);
        this.empty = true;
        this.full = false;
    }

    public synchronized void put(Client client) {
        while (full) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        store.add(Objects.requireNonNull(client));
        counter++;
        System.out.println("from put: counter = " + counter);
        if (counter == bufferLimit) {
            System.out.println("buffer is full!");
            full = true;
            notifyAll();
        }
        if (counter > 0) {
            empty = false;
            notifyAll();
        }
    }

    public synchronized Client get(ClientKey key) {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        Client findClient = store.getClient(Objects.requireNonNull(key));
        if (findClient != null) {
            counter--;
        }
        System.out.println("from get: counter = " + counter);
        if (counter == 0) {
            System.out.println("buffer is empty!");
            empty = true;
            notifyAll();
        }
        if (counter < bufferLimit) {
            full = false;
            notifyAll();
        }
        return findClient;
    }

    public String toString() {
        return store.toString();
    }

}
