package client.entities;

import java.io.*;
import java.util.Objects;

//TODO: we need serialization to be fast, may be choose another interface?
// nothing wrong here but it won't suit for fast processing.
// make sure you have no warnings here
public class Order implements Externalizable {

    private OrderKey key;
    private String comment;

    public Order() {}
    
    //TODO: implement the copy constructor
    public Order(Order order) {}
    

    // TODO are we allowed to have nulls? Please place your reply in a separate utility class
    public Order(OrderKey key, String comment) {
        this.key = Objects.requireNonNull(key);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = Objects.requireNonNull(comment);
    }

    public OrderKey getKey() {
        return key;
    }

    public void setKey(OrderKey key) {
        this.key = Objects.requireNonNull(key);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(key);
        out.writeObject(comment);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.key = (OrderKey) in.readObject();
        this.comment = (String) in.readObject();
    }

    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (!(anObject instanceof Order)) {
            return false;
        }

        Order anotherOrder = (Order) anObject;
        return key.equals(anotherOrder.getKey());
    }

    public int hashCode() {
        return key.hashCode();
    }

}
