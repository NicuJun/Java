package com.library.comparators;

import com.library.model.Reader;
import java.util.Comparator;

public class ReaderComparators {

    // За ID (зростання)
    public static final Comparator<Reader> BY_ID =
            Comparator.comparingInt(Reader::readerId);

    // За ID (спадання)
    public static final Comparator<Reader> BY_ID_DESC =
            BY_ID.reversed();

    // За прізвищем
    public static final Comparator<Reader> BY_LAST_NAME =
            Comparator.comparing(Reader::lastName);

    // За ім'ям
    public static final Comparator<Reader> BY_FIRST_NAME =
            Comparator.comparing(Reader::firstName);

    // За повним ім'ям (прізвище + ім'я)
    public static final Comparator<Reader> BY_FULL_NAME =
            Comparator.comparing(Reader::lastName)
                    .thenComparing(Reader::firstName);

    // Лямбда: за парністю ID
    public static Comparator<Reader> byIdParity() {
        return (r1, r2) -> {
            boolean even1 = r1.readerId() % 2 == 0;
            boolean even2 = r2.readerId() % 2 == 0;
            return Boolean.compare(even2, even1); // парні спочатку
        };
    }
}
