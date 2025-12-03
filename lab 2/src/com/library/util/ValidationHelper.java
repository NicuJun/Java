package com.library.util;

class ValidationHelper {
    static boolean isNonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
    static boolean isYearValid(int year) {
        return year >= 1200 && year <= java.time.LocalDate.now().getYear();
    }
    static boolean isISBN(String s) {
        return s != null && s.matches("\\d{9,13}");
    }
    static boolean isPositive(int value) { return value > 0; }
}
