package client.entities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class OrderKey implements Externalizable {
    private String num;
    private String date;

    protected OrderKey() {}

    public OrderKey(String num, String date) {
        this.num = num;
        this.date = date;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(num);
        out.writeObject(date);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        num = (String) in.readObject();
        date = (String) in.readObject();
    }

    public String getNum() {
        return num;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (! (anObject instanceof OrderKey)) {
            return false;
        }

        OrderKey anotherOrderKey = (OrderKey) anObject;

        return Objects.equals(this.num, anotherOrderKey.getNum())
                && Objects.equals(this.date, anotherOrderKey.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, date);
    }

}
