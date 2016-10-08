package Utils;

/**
 * Created by swc on 9/20/16
 * Updated by EL
 * Updated by swc on 9/26/16
 */
public class HelperUtils {

    public static final int NOT_INITIALIZED = -1;

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
}