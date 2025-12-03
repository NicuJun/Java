package com.library.model;

import com.library.util.Utils;

public record Author(String firstName, String lastName, int birthYear) {
    public Author {
        Utils.validateNotEmpty(firstName, "Ім'я");
        Utils.validateNotEmpty(lastName, "Прізвище");
        Utils.validateYear(birthYear);
    }

    public static Author of(String first, String last, int year) {
        return new Author(first, last, year);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + birthYear + ")";
    }
}
