package client.entities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class Order implements Externalizable {

    private OrderKey key;
    private String comment;

    protected Order() {}

    public Order(Order order) {
        this(order.getKey(), order.getComment());
    }

    public Order(OrderKey key, String comment) {
        this.key = Objects.requireNonNull(key);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
