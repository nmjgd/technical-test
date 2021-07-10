package com.example.technicaltest.util;

import java.time.Instant;

public final class Utils {

    private Utils() {
    }

    public static long now() {
        return Instant.now().getEpochSecond();
    }

}
