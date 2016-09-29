package Utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by swc on 9/20/16
 * Updated by EL
 * Updated by swc on 9/26/16
 */
public class HelperFunctions {


    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }


}
