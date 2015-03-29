package client.entities;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntitiesUtils {

    public static final String CLIENT_NAME_PATTERN = "[a-zA-Z]{1,20}";
    public static final String CLIENT_SURNAME_PATTERN = "[a-zA-Z]{1,20}";
    public static final String CLIENT_PASSPORT_PATTERN = "[A-Z0-9]{5}";
    public static final String ORDER_NUM_PATTERN = "[0-9]+";
    public static final String ORDER_DATE_PATTERN = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
    public static final String ORDER_COMMENT_PATTERN = ".+";

    public static boolean checkStringWithRegExp(String str, String pattern) {
        Objects.requireNonNull(pattern);
        Pattern p = Pattern.compile("^" + pattern + "$");
        Matcher m = p.matcher(Objects.requireNonNull(str));
        return m.matches();
    }

}
