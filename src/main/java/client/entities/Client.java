package client.entities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Client implements Externalizable {

    private ClientKey key;
    private String comment;
    private Map<OrderKey, Order> orders;

    protected Client() {
    }

    public Client(Client client) {
        this(client.getKey(), client.getComment());
        for (Map.Entry<OrderKey, Order> e : client.orders.entrySet()) {
            orders.put(e.getKey(), new Order(e.getValue()));
        }
    }

    public Client(ClientKey key, String comment) {
        this(key, comment, new HashMap<OrderKey, Order>());
    }

    public Client(ClientKey key, String comment, Map<OrderKey, Order> orders) {
        this.key = Objects.requireNonNull(key);
        this.comment = comment;
        this.orders = Objects.requireNonNull(orders);
    }

    public Map<OrderKey, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<OrderKey, Order> orders) {
        this.orders = orders;
    }

    public ClientKey getKey() {
        return key;
    }

    public void setKey(ClientKey key) {
        this.key = Objects.requireNonNull(key);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (!(anObject instanceof Client)) {
            return false;
        }

        Client anotherClient = (Client) anObject;
        return key.equals(anotherClient.getKey());
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        key.writeExternal(out);
        out.writeObject(comment);

        out.writeInt(orders.size());
        for (Map.Entry<OrderKey, Order> e : orders.entrySet()) {
            e.getKey().writeExternal(out);
            e.getValue().writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        key = new ClientKey();
        key.readExternal(in);
        this.comment = (String) in.readObject();

        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            OrderKey key = new OrderKey();
            key.readExternal(in);
            Order order = new Order();
            order.readExternal(in);
            orders.put(key, order);
        }
    }

}
