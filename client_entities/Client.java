package client_entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements Serializable {

    //FIXME: my manager says we're going to have 1000000 orders. How would I search thought them?
    private ArrayList<Order> orders = new ArrayList<Order>();
    private String name;
    private String surname;
    private String passport;

    public Client() {}

    public Client(String name, String surname, String passport) {
        //FIXME: hmmm, sounds a bit cheesy        
        if (checkWithRegExp(name, surname, passport)) {
            this.name = name;
            this.surname = surname;
            this.passport = passport;
        }
    }

    //FIXME: hmmm, you may lose you data & get a memory leak
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        //TODO: and if it exists? Is it a POJO or a service?
        orders.add(order);
    }

    // FIXME: why public?
/*
I would think of it as a simple POJO with no checks and it would be a problem of a service that
operates on the object to care about all the validation issues
*/
    public static boolean checkWithRegExp(String name, String surname, String passport) { //todo: завести эксепшн
        //FIXME: may be validate separately & put strings to constants?
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
