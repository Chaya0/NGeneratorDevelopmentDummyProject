package com.lms.utils;

import java.time.Duration;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Getter;

public class AppUtil {
    @Getter
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private AppUtil() {
    }

    public static String encodePassword(String raw) {
        return passwordEncoder.encode(raw);
    }

    public static String durationToString(Duration duration) {
        if (duration == null)
            return "-";
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static <K, V> Map<K, V> mergeMaps(Map<K, V> source, Map<K, V> destination) {
        destination.putAll(source);
        return destination;
    }
}
