package client_entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements Serializable {

    private ArrayList<Order> orders = new ArrayList<Order>();
    private String name;
    private String surname;
    private String passport;

    public Client() {}

    public Client(String name, String surname, String passport) {
        if (checkWithRegExp(name, surname, passport)) {
            this.name = name;
            this.surname = surname;
            this.passport = passport;
        }
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public static boolean checkWithRegExp(String name, String surname, String passport) { //todo: завести эксепшн
        Pattern nameAndSurPattern = Pattern.compile("^[a-zA-Z]{1,20}$");
        Pattern passportPattern = Pattern.compile("^[A-Z0-9]{5}$");

        Matcher nameMatcher = nameAndSurPattern.matcher(name);
        Matcher surnameMatcher = nameAndSurPattern.matcher(surname);
        Matcher passportMatcher = passportPattern.matcher(passport);

        return nameMatcher.matches()
            && surnameMatcher.matches()
            && passportMatcher.matches();
    }

}
