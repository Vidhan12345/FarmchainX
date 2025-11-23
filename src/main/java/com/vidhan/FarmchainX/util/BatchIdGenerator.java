package com.vidhan.FarmchainX.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BatchIdGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Random RANDOM = new Random();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Generates a unique batch ID in the format: BATCH-YYYYMMDD-RANDOM
     * Example: BATCH-20250123-A1B2C3
     */
    public static String generate() {
        String datePart = LocalDate.now().format(DATE_FORMATTER);
        String randomPart = generateRandomString(6);
        return "BATCH-" + datePart + "-" + randomPart;
    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
