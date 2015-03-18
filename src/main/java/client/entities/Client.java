package client.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Client implements Serializable {

    // anton: let's use the number & the date to search through the orders (I just would like you to use a custom key)
    // explain why LinkedHashMap
    // user OrderKey here as a key
    // Alex: LinkedHashMap, т.к. думал, что неплохо бы хранить в порядке вставки заказов. Только эта причина.
    private LinkedHashMap<OrderKey, Order> orders = new LinkedHashMap<OrderKey, Order>();
    private String name;
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
    // Alex: вот здесь вопрос. Во-первых, в коллекциях доступен такой же хак) Что в нем плохого?
    // во-вторых, я вижу два варианта решения:
    //          1) убрать сеттеры из Order. Тогда мы получим гемморой с обратным пушем ордера, если что-то в нем поменяем;
    //          2) выдавать клон коллекции orders, но тогда мы памяти что-то много используем непонятно зачем.
    public LinkedHashMap<OrderKey, Order> getOrders() {
        return orders;
    }

    public void setOrders(LinkedHashMap<OrderKey, Order> orders) {
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

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Client) {
            Client anotherClient = (Client) anObject;
            if (Objects.equals(this.name, anotherClient.getName())
             && Objects.equals(this.surname, anotherClient.getSurname())
             && Objects.equals(this.passport, anotherClient.getPassport()))
                return true;
        }
        return false;
    }

}
