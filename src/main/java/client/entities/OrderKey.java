package client.entities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

//TODO: create an immutable serializable key 
public class OrderKey implements Externalizable {
    private String num;
    private String date;

    // Alex: Звучит странно, но десериализация работает без сеттеров!!!

    public OrderKey() {}

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
    /*
    public void setNum(String num) {
        this.num = num;
    }
    */
    public String getDate() {
        return date;
    }
    /*
    public void setDate(String date) {
        this.date = date;
    }
    */

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof OrderKey) {
            OrderKey anotherOrderKey = (OrderKey) anObject;
            if (Objects.equals(this.num, anotherOrderKey.getNum())
             && Objects.equals(this.date, anotherOrderKey.getDate()))
                return true;
        }
        return false;
    }

}
