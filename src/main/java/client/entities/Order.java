package client.entities;

import java.io.Serializable;

//TODO: we need serialization to be fast, may be choose another interface?
// nothing wrong here but it won't suit for fast processing.
// make sure you have no warnings here
public class Order implements Serializable {

    // TODO: add here OrderKey
    private String num;
    private String date;
    private String comment;

    public Order() {}

    // TODO are we allowed to have nulls? Please place your reply in a separate utility class
    public Order(String num, String date, String comment) {
        this.num = num;
        this.date = date;
        this.comment = comment;
    }

    public String getNum() {
        return num;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

}
