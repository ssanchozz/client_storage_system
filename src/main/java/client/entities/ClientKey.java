package client.entities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import utils.StringUtils;

public class ClientKey implements Externalizable {
    private static final String WRONG_NAME = "Wrong name format %s";
    private static final String WRONG_SURNAME = "Wrong surname format %s";
    private static final String WRONG_PASSPORT = "Wrong passport format %s";

    private String name;
    private String surname;
    private String passport;

    protected ClientKey() {
    }

    public ClientKey(String name, String surname, String passport) {
        if (!StringUtils.safeCheckString(name, StringUtils.CLIENT_NAME_PATTERN)) {
            throw new IllegalArgumentException(String.format(WRONG_NAME, name));
        }
        if (!StringUtils.safeCheckString(surname, StringUtils.CLIENT_SURNAME_PATTERN)) {
            throw new IllegalArgumentException(String.format(WRONG_SURNAME, surname));
        }
        if (!StringUtils.safeCheckString(passport, StringUtils.CLIENT_PASSPORT_PATTERN)) {
            throw new IllegalArgumentException(String.format(WRONG_PASSPORT, passport));
        }
        this.name = name;
        this.surname = surname;
        this.passport = passport;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(surname);
        out.writeObject(passport);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        name = (String) in.readObject();
        surname = (String) in.readObject();
        passport = (String) in.readObject();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassport() {
        return passport;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (!(anObject instanceof ClientKey)) {
            return false;
        }

        ClientKey anotherClientKey = (ClientKey) anObject;

        return Objects.equals(this.name, anotherClientKey.getName())
                && Objects.equals(this.surname, anotherClientKey.getSurname())
                && Objects.equals(this.passport, anotherClientKey.getPassport());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, passport);
    }

}
