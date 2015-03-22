package ext.systems;

import client.entities.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public class ExtSystemOneFileParser extends Parser {

    private File fileToParse;
    /*
    @Override
    public TreeSet<Client> readData() throws Exception{

        BufferedReader reader =
                new BufferedReader(
                        new FileReader("E:\\Java_projects\\Algorithms_course\\client_storage_system\\src\\main\\resources\\client_order.type1"));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        System.out.println(lines);

        return null;
    }   */

    @Override
    void setFile(File file) {
        fileToParse = Objects.requireNonNull(file);
    }

}
