package client.entities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Client implements Externalizable {

    private ClientKey key;
    private String comment;

    private Map<OrderKey, Order> orders;

    protected Client() {
    }

    public Client(Client client) {
        // FIXME: is the key mutable?
        // how about making use of Client(ClientKey key, String comment, Map<OrderKey, Order> orders)
        this(new ClientKey(client.getKey().getName(),
                        client.getKey().getSurname(),
                        client.getKey().getPassport()),
                client.getComment());
        
        // FIXME: NEVER EVER ALLOW THE EXECUTION FLOW TO LEAVE THE CONSTRUCTOR. DANGEROUS!!!
        // check the unit tests.
        // this is dangerous as somebody may override you, and you will fall apart!
        setOrders(client.getOrders());
    }

    public Client(ClientKey key, String comment) {
        this(key, comment, new HashMap<OrderKey, Order>());
    }

    public Client(ClientKey key, String comment, Map<OrderKey, Order> orders) {
        this.key = Objects.requireNonNull(key);
        this.comment = comment;
        this.orders = Objects.requireNonNull(orders);
    }

    public List<Order> getOrders() {
        List<Order> result = new ArrayList<Order>();
        for (Order o : orders.values()) {
            result.add(new Order(o));
        }
        return result;
    }

    public void setOrders(List<Order> source) {
        Objects.requireNonNull(source);
        orders.clear();
        for (Order o : source) {
            this.orders.put(o.getKey(), o);
        }
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
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        this.key = (ClientKey) in.readObject();
        this.comment = (String) in.readObject();
    }

}
