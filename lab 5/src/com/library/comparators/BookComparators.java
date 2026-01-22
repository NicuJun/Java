package com.library.comparators;

import com.library.model.Book;
import com.library.enums.BookStatus;
import java.util.Comparator;

public class BookComparators {

    // За назвою
    public static final Comparator<Book> BY_TITLE =
            Comparator.comparing(Book::getTitle);

    // За ISBN
    public static final Comparator<Book> BY_ISBN =
            Comparator.comparing(Book::getIsbn);

    // За статусом
    public static final Comparator<Book> BY_STATUS =
            Comparator.comparing(Book::getStatus);

    // За кількістю авторів
    public static final Comparator<Book> BY_AUTHORS_COUNT =
            Comparator.comparingInt(b -> b.getAuthors().size());

    // За довжиною назви
    public static Comparator<Book> byTitleLength() {
        return Comparator.comparingInt(b -> b.getTitle().length());
    }

    // Спочатку доступні книги
    public static Comparator<Book> availableFirst() {
        return (b1, b2) -> {
            boolean av1 = b1.getStatus() == BookStatus.AVAILABLE;
            boolean av2 = b2.getStatus() == BookStatus.AVAILABLE;
            if (av1 && !av2) return -1;
            if (!av1 && av2) return 1;
            return b1.getTitle().compareTo(b2.getTitle());
        };
    }
}
