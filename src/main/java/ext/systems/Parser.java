package ext.systems;

import java.io.File;

public interface Parser {

    // If you meant 'setDirectory', I would understand.
    // this one won't work in a multithreaded environment.
    public void setFile(File file);

}
