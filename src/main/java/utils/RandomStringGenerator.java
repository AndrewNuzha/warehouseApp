package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringGenerator {

    private final static int STRING_LENGTH = 10;

    public static String generateString() {
        return RandomStringUtils.randomAlphanumeric(STRING_LENGTH);
    }

}
