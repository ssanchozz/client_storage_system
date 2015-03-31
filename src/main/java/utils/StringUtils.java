package utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static final String CLIENT_NAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String CLIENT_SURNAME_PATTERN = "^[a-zA-Z]{1,20}$";
    public static final String CLIENT_PASSPORT_PATTERN = "^[A-Z0-9]{5}$";
    public static final String ORDER_NUM_PATTERN = "^[0-9]+$";
    public static final String ORDER_DATE_PATTERN = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
    public static final String ORDER_COMMENT_PATTERN = "^.+$";

    public static final String WRONG_PATTERN = "pattern must not be null";
    public static final String MATCH_ERROR = "The string %s does not match the pattern %s";

    public static boolean safeCheckString(String str, String pattern) {
        Objects.requireNonNull(pattern, WRONG_PATTERN);
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(Objects.requireNonNull(str));
        return m.matches();
    }

    public static String checkString(String str, String pattern) {
        if (safeCheckString(str, pattern)) {
            return str;
        }

        throw new RuntimeException(String.format(MATCH_ERROR, str, pattern));
    }

}
