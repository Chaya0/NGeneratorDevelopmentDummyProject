package com.lms.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeParser {
    private static final String DATE_TIME_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}$";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

    private LocalDateTimeParser() {
    }

    public static LocalDateTime parse(String dateTimeString) {
        if (!matches(dateTimeString)) {
            throw new IllegalArgumentException("Invalid date format: " + dateTimeString);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        try {
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateTimeString, e);
        }
    }

    public static boolean matches(String dateTimeString) {
        return dateTimeString.matches(DATE_TIME_REGEX);
    }
}
