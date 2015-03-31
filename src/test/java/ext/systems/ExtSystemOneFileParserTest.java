package ext.systems;

import client.entities.Client;
import org.junit.Before;
import org.junit.Test;

public class ExtSystemOneFileParserTest {

    private Parser parser;

    @Before
    public void setup() {
        parser = new ExtSystemOneFileParser(";");
    }

    @Test
    public void testParse() {
        //FIXME: use files instead! this is quite ugly
        
        String line1 = "John;Doe;12QA5;1;2014-06-06;order1";
        String line2 = "John;Doe;12QA5;1;2014-06-06;duplicated order1";
        String line3 = "what a mess!";
        String line4 = "John;Doe;12QA5;9;2014-06-10;order8";
        String line5 = "John;Doe;12QA5;5;2014-06-06;order7";
        String line6 = "John;Doe;1;5;2014-06-06;order7";
        String line7 = "John3;Doe3;12QA5;;;";
        String line8 = "John1;Doe1;32QA5;99;2010-06-06;order2";
        String line9 = ";;";
        String line10 = "John1;Doe1;32QA5;3;2014-06-07;order2";
        Client client1 = parser.parse(line1);
        Client client2 = parser.parse(line2);
        Client client3 = parser.parse(line3);
        Client client4 = parser.parse(line4);
        Client client5 = parser.parse(line5);
        Client client6 = parser.parse(line6);
        Client client7 = parser.parse(line7);
        Client client8 = parser.parse(line8);
        Client client9 = parser.parse(line9);
        Client client10 = parser.parse(line10);
        System.out.println(client1);
        System.out.println(client2);
        System.out.println(client3);
        System.out.println(client4);
        System.out.println(client5);
        System.out.println(client6);
        System.out.println(client7);
        System.out.println(client8);
        System.out.println(client9);
        System.out.println(client10);
    }


}
