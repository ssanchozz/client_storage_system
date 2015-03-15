// FIXME: I'd suggest 'package domain' as it has domain objects
package client_entities;

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

    // TODO: how would I get the details of an order?

}
