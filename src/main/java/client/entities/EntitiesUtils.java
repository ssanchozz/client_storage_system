package client.entities;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntitiesUtils {

    public static final String NAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String SURNAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String PASSPORT_PATTERN = "^[A-Z0-9]{5}$";

    public static boolean checkStringWithRegExp(String str, String pattern) {
        Pattern p = Pattern.compile(Objects.requireNonNull(pattern));
        Matcher m = p.matcher(Objects.requireNonNull(str));
        return m.matches();
    }

}
