package com.library.model;

import com.library.util.Utils;
import java.util.Objects;

public class Author {
    protected String firstName;
    protected String lastName;
    protected int birthYear;

    public Author(String firstName, String lastName, int birthYear) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthYear(birthYear);
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { Utils.validateNotEmpty(firstName, "Ім'я"); this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { Utils.validateNotEmpty(lastName, "Прізвище"); this.lastName = lastName; }

    public int getBirthYear() { return birthYear; }
    protected void setBirthYear(int birthYear) { Utils.validateYear(birthYear); this.birthYear = birthYear; }

    @Override public String toString() {
        return firstName + " " + lastName + " (" + birthYear + ")";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author a = (Author) o;
        return birthYear == a.birthYear && Objects.equals(firstName, a.firstName) && Objects.equals(lastName, a.lastName);
    }
    @Override public int hashCode() { return Objects.hash(firstName, lastName, birthYear); }

    public static Author of(String first, String last, int year) {
        return new Author(first, last, year);
    }
}
