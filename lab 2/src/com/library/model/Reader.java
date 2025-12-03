package com.library.model;

import com.library.util.Utils;

public record Reader(String firstName, String lastName, int readerId) {
    public Reader {
        Utils.validateNotEmpty(firstName, "Ім'я");
        Utils.validateNotEmpty(lastName, "Прізвище");
        Utils.validatePositive(readerId, "readerId");
    }

    public static Reader of(String f, String l, int id) {
        return new Reader(f, l, id);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " [ID: " + readerId + "]";
    }
}
