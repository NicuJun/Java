package com.library;

import com.library.model.Author;
import com.library.repository.AuthorRepository;
import java.util.List;

public class AuthorStreamTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ STREAM API: АВТОРИ ===\n");

        testFilter();
        testMap();
        testReduce();
        testCount();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testFilter() {
        System.out.println("Тест 1: Filter - пошук за діапазоном років");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("A", "B", 1850));
        repo.add(new Author("C", "D", 1900));
        repo.add(new Author("E", "F", 1875));

        List<Author> filtered = repo.findByBirthYearRange(1850, 1880);
        assert filtered.size() == 2 : "Має знайтись 2 авторів";

        System.out.println("✓ Знайдено " + filtered.size() + " авторів");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testMap() {
        System.out.println("Тест 2: Map - отримання прізвищ");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("Іван", "Франко", 1856));
        repo.add(new Author("Леся", "Українка", 1871));
        repo.add(new Author("Тарас", "Шевченко", 1814));

        List<String> lastNames = repo.getAllLastNames();
        assert lastNames.size() == 3 : "Має бути 3 прізвища";
        assert lastNames.contains("Франко") : "Має бути Франко";

        System.out.println("✓ Отримано " + lastNames.size() + " прізвищ");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testReduce() {
        System.out.println("Тест 3: Reduce - пошук найстарішого");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("A", "B", 1871));
        repo.add(new Author("C", "D", 1814));
        repo.add(new Author("E", "F", 1856));

        var oldest = repo.getOldestAuthor();
        assert oldest.isPresent() : "Має знайтись найстаріший";
        assert oldest.get().birthYear() == 1814 : "Найстаріший - 1814";

        System.out.println("✓ Найстаріший: " + oldest.get().birthYear());
        System.out.println("✓ Тест пройдено\n");
    }

    static void testCount() {
        System.out.println("Тест 4: Count - підрахунок");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("A", "B", 1850));
        repo.add(new Author("C", "D", 1900));
        repo.add(new Author("E", "F", 1875));

        long count = repo.countOlderThan(1880);
        assert count == 2 : "Має бути 2 авторів старших за 1880";

        System.out.println("✓ Кількість: " + count);
        System.out.println("✓ Тест пройдено\n");
    }
}
