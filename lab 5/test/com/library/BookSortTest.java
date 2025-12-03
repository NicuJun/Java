package com.library;

import com.library.model.*;
import com.library.enums.BookStatus;
import com.library.repository.BookRepository;
import java.util.List;

public class BookSortTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ СОРТУВАННЯ КНИГ ===\n");

        testSortByTitle();
        testSortByIsbn();
        testComparable();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testSortByTitle() {
        System.out.println("Тест 1: Сортування за назвою");
        BookRepository repo = new BookRepository();
        Author a = new Author("Test", "Test", 1900);

        repo.add(new Book("Яблуко", List.of(a), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Апельсин", List.of(a), "2222222222222", BookStatus.AVAILABLE));
        repo.add(new Book("Груша", List.of(a), "3333333333333", BookStatus.AVAILABLE));

        List<Book> sorted = repo.sortByTitle();

        assert sorted.get(0).getTitle().equals("Апельсин") : "Перша має бути 'Апельсин'";
        assert sorted.get(1).getTitle().equals("Груша") : "Друга має бути 'Груша'";
        assert sorted.get(2).getTitle().equals("Яблуко") : "Третя має бути 'Яблуко'";

        System.out.println("✓ Тест пройдено\n");
    }

    static void testSortByIsbn() {
        System.out.println("Тест 2: Сортування за ISBN");
        BookRepository repo = new BookRepository();
        Author a = new Author("T", "T", 1900);

        repo.add(new Book("Book1", List.of(a), "3333333333333", BookStatus.AVAILABLE));
        repo.add(new Book("Book2", List.of(a), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Book3", List.of(a), "2222222222222", BookStatus.AVAILABLE));

        List<Book> sorted = repo.sortByIsbn();

        assert sorted.get(0).getIsbn().equals("1111111111111") : "Перший ISBN";
        assert sorted.get(1).getIsbn().equals("2222222222222") : "Другий ISBN";
        assert sorted.get(2).getIsbn().equals("3333333333333") : "Третій ISBN";

        System.out.println("✓ Тест пройдено\n");
    }

    static void testComparable() {
        System.out.println("Тест 3: Реалізація Comparable");
        Author a = new Author("T", "T", 1900);

        Book b1 = new Book("AAA", List.of(a), "1111111111111", BookStatus.AVAILABLE);
        Book b2 = new Book("ZZZ", List.of(a), "2222222222222", BookStatus.AVAILABLE);
        Book b3 = new Book("MMM", List.of(a), "3333333333333", BookStatus.AVAILABLE);

        assert b1.compareTo(b2) < 0 : "AAA < ZZZ";
        assert b2.compareTo(b1) > 0 : "ZZZ > AAA";
        assert b1.compareTo(b3) < 0 : "AAA < MMM";

        System.out.println("✓ Comparable працює коректно");
        System.out.println("✓ Тест пройдено\n");
    }
}
