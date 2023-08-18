package com.example.fashionshop.utils;

public class NumberUtils {

    public static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
