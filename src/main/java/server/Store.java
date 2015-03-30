package server;

import java.util.Collection;
import java.util.Iterator;

import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;

public interface Store {
    void add(Client client);

    void addOrders(ClientKey key, Order order);

    void addOrders(ClientKey key, Collection<Order> orders);

    Client find(ClientKey key);

    Iterator<Client> iterator();
}
