package client.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Client implements Serializable {

    // anton: let's use the number & the date to search through the orders (I just would like you to use a custom key)
    // explain why LinkedHashMap
    // user OrderKey here as a key
    private LinkedHashMap<String, Order> orders = new LinkedHashMap<String, Order>(); // Мапа будет с номером заказа и заказом
    private String name;                                                              // номер заказа - ключ, однозначно идентифицирует заказ клиента
    private String surname;
    private String passport;

    public Client() {}

    public Client(String name, String surname, String passport) {
        this.name = name;
        this.surname = surname;
        this.passport = passport;
    }

    // FIXME: I could break this code by getting a client from the store,
    // changing its orders and... that's it! I don't have to push it back...
    // everything will be done for me. Nice hacking!
    public LinkedHashMap<String, Order> getOrders() {
        return orders;
    }

    public void setOrders(LinkedHashMap<String, Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    //TODO: you've broken the contract for equals
    // and in addition it's NPE-prone. Use java.util.Objects for checks
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Client) {
            Client anotherClient = (Client) anObject;
            if (this.name.equals(anotherClient.getName())
             && this.surname.equals(anotherClient.getSurname())
             && this.passport.equals(anotherClient.getPassport()))
                return true;
        }
        return false;
    }

}
