package client.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntitiesUtils {

    public static final String NAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String SURNAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String PASSPORT_PATTERN = "^[A-Z0-9]{5}$";

    public static boolean checkNameWithRegExp(String name) {
        Pattern namePattern = Pattern.compile(NAME_PATTERN);
        Matcher nameMatcher = namePattern.matcher(name);
        return nameMatcher.matches();
    }

    public static boolean checkSurnameWithRegExp(String surname) {
        Pattern surnamePattern = Pattern.compile(SURNAME_PATTERN);
        Matcher surnameMatcher = surnamePattern.matcher(surname);
        return surnameMatcher.matches();
    }

    public static boolean checkPassportWithRegExp(String passport) {
        Pattern passportPattern = Pattern.compile(PASSPORT_PATTERN);
        Matcher passportMatcher = passportPattern.matcher(passport);
        return passportMatcher.matches();
    }

}
