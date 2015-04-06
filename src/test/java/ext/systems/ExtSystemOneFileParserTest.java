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

    }


}
