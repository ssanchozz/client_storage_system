package ext.systems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import utils.StringUtils;
import client.entities.Client;
import client.entities.ClientKey;
import client.entities.Order;
import client.entities.OrderKey;

public class ExtSystemOneFileParser implements Parser {

    private static final int NAME_IDX = 0;
    private static final int SURNAME_IDX = 1;
    private static final int PASSPORT_IDX = 2;
    private static final int NUM_IDX = 3;
    private static final int DATE_IDX = 4;
    private static final int COMMENT_IDX = 5;

//    private static final Logger logger =
//            LogManager.getLogger(ExtSystemOneFileParser.class);

    private final String separator;

    public ExtSystemOneFileParser(String separator) {
        this.separator = separator;
    }

    @Override
    public List<Client> parse(File file) {
        Map<ClientKey, Client> clients = new HashMap<>();
        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Client client = parseLine(line);
                if (client != null) {
                    ClientKey key = client.getKey();
                    clients.put(
                            key,
                            merge(
                                    client,
                                    clients.get(key)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log the error, the file cannot be parsed
        }
        return new ArrayList<>(clients.values());
    }

    private Client parseLine(String line) {
        Client client = null;

        try {
            String[] token = line.split(separator);

            String name = StringUtils.checkString(
                    token[NAME_IDX],
                    StringUtils.CLIENT_NAME_PATTERN);
            String surname = StringUtils.checkString(
                    token[SURNAME_IDX],
                    StringUtils.CLIENT_SURNAME_PATTERN);
            String passport = StringUtils.checkString(
                    token[PASSPORT_IDX],
                    StringUtils.CLIENT_PASSPORT_PATTERN);
            String num = StringUtils.checkString(
                    token[NUM_IDX],
                    StringUtils.ORDER_NUM_PATTERN);
            String date = StringUtils.checkString(
                    token[DATE_IDX],
                    StringUtils.ORDER_DATE_PATTERN);
            String comment = StringUtils.checkString(
                    token[COMMENT_IDX],
                    StringUtils.ORDER_COMMENT_PATTERN);

            ClientKey clientKey = new ClientKey(name, surname, passport);
            OrderKey orderKey = new OrderKey(num, date);
            Order order = new Order(orderKey, comment);
            Map<OrderKey, Order> orders = new HashMap<>();
            orders.put(orderKey, order);

            client = new Client(clientKey, comment, orders);
        } catch (RuntimeException e) {
            //TODO: log the error and ignore the line
        }

        return client;
    }

    private Client merge(Client source, Client toAdd) {
        if (toAdd == null) {
            return source;
        }
        // in fact, only orders need to be added. Let's take the latest comment.
        source.getOrders().putAll(toAdd.getOrders());
        return source;
    }

}
