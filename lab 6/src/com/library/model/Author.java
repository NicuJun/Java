package com.library.model;

import com.library.util.Utils;

public record Author(String firstName, String lastName, int birthYear)
        implements Comparable<Author> {

    public Author {
        Utils.validateNotEmpty(firstName, "Ім'я");
        Utils.validateNotEmpty(lastName, "Прізвище");
        Utils.validateYear(birthYear);
    }

    @Override
    public int compareTo(Author other) {
        int lastNameCompare = this.lastName.compareTo(other.lastName);
        if (lastNameCompare != 0) {
            return lastNameCompare;
        }
        return this.firstName.compareTo(other.firstName);
    }

    public static Author of(String first, String last, int year) {
        return new Author(first, last, year);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + birthYear + ")";
    }
}
