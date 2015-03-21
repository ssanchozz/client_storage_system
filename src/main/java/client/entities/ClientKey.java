package client.entities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class ClientKey implements Externalizable {

    private String name;
    private String surname;
    private String passport;

    protected ClientKey() {
    }

    public ClientKey(String name, String surname, String passport) {
        if (!EntitiesUtils.checkNameWithRegExp(Objects.requireNonNull(name))) {
            throw new IllegalArgumentException("Wrong name format!");
        }
        if (!EntitiesUtils.checkNameWithRegExp(Objects.requireNonNull(surname))) {
            throw new IllegalArgumentException("Wrong surname format!");
        }
        if (!EntitiesUtils.checkNameWithRegExp(Objects.requireNonNull(passport))) {
            throw new IllegalArgumentException("Wrong passport format!");
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
                && Objects
                        .equals(this.passport, anotherClientKey.getPassport());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, passport);
    }

}
