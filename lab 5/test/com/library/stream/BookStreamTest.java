package com.library;

import com.library.model.*;
import com.library.enums.BookStatus;
import com.library.repository.BookRepository;
import java.util.*;

public class BookStreamTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ STREAM API: КНИГИ ===\n");

        testFilter();
        testMap();
        testFlatMap();
        testGrouping();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testFilter() {
        System.out.println("Тест 1: Filter - пошук за статусом");
        BookRepository repo = new BookRepository();
        Author a = new Author("Test", "Test", 1900);

        repo.add(new Book("Book1", List.of(a), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Book2", List.of(a), "2222222222222", BookStatus.CHECKED_OUT));
        repo.add(new Book("Book3", List.of(a), "3333333333333", BookStatus.AVAILABLE));

        List<Book> available = repo.findAvailableBooks();
        assert available.size() == 2 : "Має бути 2 доступні книги";

        System.out.println("✓ Знайдено " + available.size() + " доступних книг");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testMap() {
        System.out.println("Тест 2: Map - отримання назв");
        BookRepository repo = new BookRepository();
        Author a = new Author("T", "T", 1900);

        repo.add(new Book("Title1", List.of(a), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Title2", List.of(a), "2222222222222", BookStatus.AVAILABLE));

        List<String> titles = repo.getAllTitles();
        assert titles.size() == 2 : "Має бути 2 назви";
        assert titles.contains("Title1") : "Має бути Title1";

        System.out.println("✓ Отримано " + titles.size() + " назв");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testFlatMap() {
        System.out.println("Тест 3: FlatMap - витягування авторів");
        BookRepository repo = new BookRepository();

        Author a1 = new Author("A1", "L1", 1900);
        Author a2 = new Author("A2", "L2", 1900);
        Author a3 = new Author("A3", "L3", 1900);

        repo.add(new Book("Book1", List.of(a1, a2), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Book2", List.of(a2, a3), "2222222222222", BookStatus.AVAILABLE));

        List<Author> allAuthors = repo.getAllAuthorsFromBooks();
        assert allAuthors.size() == 3 : "Має бути 3 унікальних авторів";

        Set<String> lastNames = repo.getAllAuthorLastNames();
        assert lastNames.size() == 3 : "Має бути 3 прізвища";

        System.out.println("✓ Унікальних авторів: " + allAuthors.size());
        System.out.println("✓ Тест пройдено\n");
    }

    static void testGrouping() {
        System.out.println("Тест 4: Grouping - групування за статусом");
        BookRepository repo = new BookRepository();
        Author a = new Author("T", "T", 1900);

        repo.add(new Book("B1", List.of(a), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("B2", List.of(a), "2222222222222", BookStatus.AVAILABLE));
        repo.add(new Book("B3", List.of(a), "3333333333333", BookStatus.CHECKED_OUT));

        Map<BookStatus, Long> grouped = repo.countBooksByStatus();
        assert grouped.get(BookStatus.AVAILABLE) == 2 : "2 AVAILABLE";
        assert grouped.get(BookStatus.CHECKED_OUT) == 1 : "1 CHECKED_OUT";

        System.out.println("✓ AVAILABLE: " + grouped.get(BookStatus.AVAILABLE));
        System.out.println("✓ CHECKED_OUT: " + grouped.get(BookStatus.CHECKED_OUT));
        System.out.println("✓ Тест пройдено\n");
    }
}
