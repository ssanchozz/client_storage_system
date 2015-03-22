package ext.systems;

import java.io.File;

public abstract class Parser {

    // If you meant 'setDirectory', I would understand.
    // this one won't work in a multithreaded environment.
    abstract void setFile(File file);

}
