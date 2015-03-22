package server;

import client.entities.Client;
import client.entities.ClientKey;

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

    public StorageBuffer(int bufferLimit, Store store) {

    }

    public void put(Client client) {
        // TODO Auto-generated method stub

    }

    public Client get(ClientKey key) {
        // TODO Auto-generated method stub
        return null;
    }

}
