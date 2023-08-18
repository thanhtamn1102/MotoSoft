package com.example.fashionshop.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtils {

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String createHashString(String text) {
        String sha256hex = Hashing.sha256()
                .hashString(text, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }

    public static String createUniqueString() {
        return UUID.randomUUID().toString();
    }


    private static final DecimalFormat formatter = new DecimalFormat("###,###,###");
    public static String formatCurrency(double d) {
        return formatter.format(d);
    }

}
