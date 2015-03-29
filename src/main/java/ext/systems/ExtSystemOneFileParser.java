package ext.systems;

import client.entities.Client;
import client.entities.ClientKey;
import client.entities.EntitiesUtils;
import client.entities.Order;
import client.entities.OrderKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public class ExtSystemOneFileParser implements Parser {

    private File fileToParse;
    private String separator = ";";
    private static final Logger logger =
            LogManager.getLogger(ExtSystemOneFileParser.class);

    @Override
    public void setFile(File file) {
        fileToParse = Objects.requireNonNull(file);
    }

    @Override
    public Map<ClientKey, Client> processFile() {
        HashMap<ClientKey, Client> clients = new HashMap<ClientKey, Client>();
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileToParse))) {
            String line;
            Client client;
            while ((line = reader.readLine()) != null) {
                client = parse(line);
                if (client != null) {
                    clients.put(client.getKey(), client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client parse(String line) {
        if (line.matches("^" + EntitiesUtils.CLIENT_NAME_PATTERN + separator +
                EntitiesUtils.CLIENT_SURNAME_PATTERN + separator +
                EntitiesUtils.CLIENT_PASSPORT_PATTERN + separator +
                EntitiesUtils.ORDER_NUM_PATTERN + separator +
                EntitiesUtils.ORDER_DATE_PATTERN + separator +
                EntitiesUtils.ORDER_COMMENT_PATTERN + "$")) {
            String[] tokens = line.split(separator);
            String clName = null, clSurname = null, clPassport = null,
                    ordNum = null, ordDate = null, ordComment = null;
            for (int i=0; i<tokens.length; i++) {
                switch (i) {
                    case 0: clName = tokens[i]; break;
                    case 1: clSurname = tokens[i]; break;
                    case 2: clPassport = tokens[i]; break;
                    case 3: ordNum = tokens[i]; break;
                    case 4: ordDate = tokens[i]; break;
                    case 5: ordComment = tokens[i]; break;
                }
            }
            ClientKey ck = new ClientKey(clName, clSurname, clPassport);
            OrderKey ok = new OrderKey(ordNum, ordDate);
            Order order = new Order(ok, ordComment);
            ArrayList<Order> orders = new ArrayList<Order>();
            orders.add(order);
            Client client = new Client(ck,"");
            client.setOrders(orders);
            return client;
        }
        //logger.warn("Line [" + line + "] in file " + fileToParse.getName() +
        // " doesn't match pattern");
        return null;
    }

}
