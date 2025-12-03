package com.library.util;

public class Utils {
    public static void validateNotEmpty(String val, String field) {
        if (!ValidationHelper.isNonEmpty(val)) {
            throw new IllegalArgumentException(field + " не може бути порожнім");
        }
    }
    public static void validateYear(int year) {
        if (!ValidationHelper.isYearValid(year)) {
            throw new IllegalArgumentException("Рік невалідний: " + year);
        }
    }
    public static void validateISBN(String isbn) {
        if (!ValidationHelper.isISBN(isbn)) {
            throw new IllegalArgumentException("Невалідний ISBN: " + isbn);
        }
    }
    public static void validatePositive(int val, String field) {
        if (!ValidationHelper.isPositive(val)) {
            throw new IllegalArgumentException(field + " має бути > 0");
        }
    }
}
