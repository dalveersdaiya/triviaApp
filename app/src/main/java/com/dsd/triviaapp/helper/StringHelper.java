package com.dsd.triviaapp.helper;

import java.util.UUID;

public class StringHelper {

    public static String uniqueId(int countOfString) {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase().substring(0, countOfString);
    }

    public static String uniqueId24() {
        return uniqueId(24);
    }

    public static String trimString(String value) {
        return value == null ? "" : value.trim();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().equals("");
    }

    public static String removeDashes(String str) {
        str = str.replaceAll("\\D+", "");
        return str;
    }


}
