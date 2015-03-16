// FIXME: I'd suggest 'package domain' as it has domain objects
package client.entities;

import java.io.Serializable;

public class Order implements Serializable {

    private String num;
    private String date;
    private String comment;

    public Order() {}

    public Order(String num, String date, String comment) {
        this.num = num;
        this.date = date;
        this.comment = comment;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
