package client.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Client implements Serializable {

    //FIXME: my manager says we're going to have 1000000 orders. How would I search thought them?
    // Немножно непонятно, что имеется ввиду. Т.е. мы будем как-то искать заказ? Как? Тоже по трем полям? Или только по номеру?
    // или и по номеру и по дате?
    // то, что я ниже поменял, пока не окончательно, подумаю, как ответишь)))
    //private ArrayList<Order> orders = new ArrayList<Order>();
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
