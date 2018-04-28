package org.theponies.ponecrafter.util;

public class StringUtils {

    private StringUtils () {
        //Private constructor
    }

    public static String capitalizeFirst (String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
