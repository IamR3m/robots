package com.alexkasko.robots.random;

import java.util.Random;

public class RandomString {
    public static String randomAlphanumericString(int targetStringLength) {
        int leftLimit = 48;
        int rightLimit = 122;

        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String randomAlphanumericString() {
        return randomAlphanumericString(8);
    }
}
