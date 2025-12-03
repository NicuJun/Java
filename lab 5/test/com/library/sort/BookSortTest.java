package com.library;

import com.library.model.*;
import com.library.enums.BookStatus;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестування сортування книг")
class BookSortTest {

    private BookRepository repo;
    private Author author;

    @BeforeEach
    void setUp() {
        repo = new BookRepository();
        author = new Author("Test", "Test", 1900);
    }

    @Test
    @DisplayName("Сортування за назвою")
    void testSortByTitle() {
        repo.add(new Book("Яблуко", List.of(author), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Апельсин", List.of(author), "2222222222222", BookStatus.AVAILABLE));
        repo.add(new Book("Груша", List.of(author), "3333333333333", BookStatus.AVAILABLE));

        List<Book> sorted = repo.sortByTitle();

        assertEquals(3, sorted.size());
        assertEquals("Апельсин", sorted.get(0).getTitle(), "Перша має бути 'Апельсин'");
        assertEquals("Груша", sorted.get(1).getTitle(), "Друга має бути 'Груша'");
        assertEquals("Яблуко", sorted.get(2).getTitle(), "Третя має бути 'Яблуко'");
    }

    @Test
    @DisplayName("Сортування за ISBN")
    void testSortByIsbn() {
        repo.add(new Book("Book1", List.of(author), "3333333333333", BookStatus.AVAILABLE));
        repo.add(new Book("Book2", List.of(author), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Book3", List.of(author), "2222222222222", BookStatus.AVAILABLE));

        List<Book> sorted = repo.sortByIsbn();

        assertEquals(3, sorted.size());
        assertEquals("1111111111111", sorted.get(0).getIsbn(), "Перший ISBN");
        assertEquals("2222222222222", sorted.get(1).getIsbn(), "Другий ISBN");
        assertEquals("3333333333333", sorted.get(2).getIsbn(), "Третій ISBN");
    }

    @Test
    @DisplayName("Реалізація Comparable")
    void testComparable() {
        Book b1 = new Book("AAA", List.of(author), "1111111111111", BookStatus.AVAILABLE);
        Book b2 = new Book("ZZZ", List.of(author), "2222222222222", BookStatus.AVAILABLE);
        Book b3 = new Book("MMM", List.of(author), "3333333333333", BookStatus.AVAILABLE);

        assertTrue(b1.compareTo(b2) < 0, "AAA < ZZZ");
        assertTrue(b2.compareTo(b1) > 0, "ZZZ > AAA");
        assertTrue(b1.compareTo(b3) < 0, "AAA < MMM");
        assertTrue(b3.compareTo(b2) < 0, "MMM < ZZZ");
    }
}
