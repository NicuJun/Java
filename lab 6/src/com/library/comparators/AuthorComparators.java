package com.library.comparators;

import com.library.model.Author;
import java.util.Comparator;

public class AuthorComparators {

    // За роком народження (зростання)
    public static final Comparator<Author> BY_BIRTH_YEAR =
            Comparator.comparingInt(Author::birthYear);

    // За роком народження (спадання)
    public static final Comparator<Author> BY_BIRTH_YEAR_DESC =
            BY_BIRTH_YEAR.reversed();

    // За прізвищем
    public static final Comparator<Author> BY_LAST_NAME =
            Comparator.comparing(Author::lastName);

    // За ім'ям
    public static final Comparator<Author> BY_FIRST_NAME =
            Comparator.comparing(Author::firstName);

    // За прізвищем, потім ім'ям
    public static final Comparator<Author> BY_FULL_NAME =
            Comparator.comparing(Author::lastName)
                    .thenComparing(Author::firstName);

    // За віком (найстаріші спочатку)
    public static Comparator<Author> byAgeDescending() {
        return (a1, a2) -> Integer.compare(a1.birthYear(), a2.birthYear());
    }

    // Лямбда: за довжиною прізвища
    public static Comparator<Author> byLastNameLength() {
        return (a1, a2) -> Integer.compare(
                a1.lastName().length(),
                a2.lastName().length()
        );
    }
}
