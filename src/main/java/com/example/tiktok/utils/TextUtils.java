package com.example.tiktok.utils;

import java.util.List;

public class TextUtils {
    public static boolean isEmpty(String s) {
        return s.isEmpty();
    }

    public static boolean isNullOrEmpty(Object input) {
        if (input instanceof String) {
            return input == null || ((String) input).isEmpty();
        }

        if (input instanceof List) {
            return input == null || ((List) input).isEmpty();
        }
        return input == null;
    }
}
