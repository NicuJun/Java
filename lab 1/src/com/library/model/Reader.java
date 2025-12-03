package com.library.model;

import com.library.util.Utils;
import java.util.Objects;

public class Reader {
    private String firstName;
    private String lastName;
    private int readerId;

    public Reader(String firstName, String lastName, int readerId) {
        setFirstName(firstName);
        setLastName(lastName);
        setReaderId(readerId);
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { Utils.validateNotEmpty(firstName, "Ім'я"); this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { Utils.validateNotEmpty(lastName, "Прізвище"); this.lastName = lastName; }

    public int getReaderId() { return readerId; }
    public void setReaderId(int readerId) { Utils.validatePositive(readerId, "ID"); this.readerId = readerId; }

    @Override public String toString() {
        return firstName + " " + lastName + " [ID: " + readerId + "]";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;
        Reader r = (Reader) o;
        return readerId == r.readerId && Objects.equals(firstName, r.firstName) && Objects.equals(lastName, r.lastName);
    }
    @Override public int hashCode() { return Objects.hash(firstName, lastName, readerId); }

    public static Reader of(String f, String l, int id) {
        return new Reader(f, l, id);
    }
}
