package com.library;

import com.library.model.*;
import com.library.enums.*;
import com.library.repository.*;
import com.library.util.Logger;
import java.util.*;

public class Main {
    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        logger.info("=".repeat(80));
        logger.info("ЗАПУСК LIBRARY SYSTEM - ЛР 6: Stream API");
        logger.info("=".repeat(80));

        demonstrateAuthorStreamAPI();
        demonstrateReaderStreamAPI();
        demonstrateBookStreamAPI();
        demonstrateFlatMap();
        demonstrateTerminalOperations();
        demonstratePerformanceComparison();

        logger.info("=".repeat(80));
        logger.info("✓ Програма завершена успішно!");
        logger.info("=".repeat(80));
    }

    private static void demonstrateAuthorStreamAPI() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. STREAM API: АВТОРИ");
        System.out.println("=".repeat(80));

        AuthorRepository repo = new AuthorRepository();
        repo.add(new Author("Іван", "Франко", 1856));
        repo.add(new Author("Леся", "Українка", 1871));
        repo.add(new Author("Тарас", "Шевченко", 1814));
        repo.add(new Author("Михайло", "Коцюбинський", 1864));
        repo.add(new Author("Іван", "Котляревський", 1769));
        repo.add(new Author("Панас", "Мирний", 1849));

        // FILTER
        System.out.println("\n--- Filter: Автори 19 століття ---");
        repo.findByBirthYearRange(1800, 1899)
                .forEach(a -> System.out.println("  • " + a));

        // MAP
        System.out.println("\n--- Map: Всі прізвища ---");
        repo.getAllLastNames()
                .forEach(name -> System.out.println("  • " + name));

        // REDUCE
        System.out.println("\n--- Reduce: Найстаріший автор ---");
        repo.getOldestAuthor()
                .ifPresent(a -> System.out.println("  • " + a));

        System.out.println("\n--- Reduce: Середній вік ---");
        System.out.println("  • Середній вік: " + String.format("%.1f", repo.getAverageAge()));

        // COUNT
        System.out.println("\n--- Count: Автори старші за 1850 ---");
        System.out.println("  • Кількість: " + repo.countOlderThan(1850));
    }

    private static void demonstrateReaderStreamAPI() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. STREAM API: ЧИТАЧІ");
        System.out.println("=".repeat(80));

        ReaderRepository repo = new ReaderRepository();
        repo.add(new Reader("Марія", "Іваненко", 105));
        repo.add(new Reader("Степан", "Петренко", 102));
        repo.add(new Reader("Ганна", "Петренко", 108));
        repo.add(new Reader("Олександр", "Коваль", 101));
        repo.add(new Reader("Анна", "Сидоренко", 110));
        repo.add(new Reader("Іван", "Петренко", 115));

        // FILTER
        System.out.println("\n--- Filter: Читачі з прізвищем 'Петренко' ---");
        repo.findByLastName("Петренко")
                .forEach(r -> System.out.println("  • " + r));

        System.out.println("\n--- Filter: ID у діапазоні 100-110 ---");
        repo.findByIdRange(100, 110)
                .forEach(r -> System.out.println("  • " + r));

        // MAP
        System.out.println("\n--- Map: Унікальні прізвища ---");
        repo.getUniqueLastNames()
                .forEach(name -> System.out.println("  • " + name));

        // REDUCE
        System.out.println("\n--- Reduce: Максимальний ID ---");
        repo.getReaderWithMaxId()
                .ifPresent(r -> System.out.println("  • " + r));

        System.out.println("\n--- Reduce: Середній ID ---");
        System.out.println("  • Середній ID: " + String.format("%.1f", repo.getAverageId()));
    }

    private static void demonstrateBookStreamAPI() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. STREAM API: КНИГИ");
        System.out.println("=".repeat(80));

        BookRepository repo = new BookRepository();
        Author franko = new Author("Іван", "Франко", 1856);
        Author ukrainka = new Author("Леся", "Українка", 1871);
        Author shevchenko = new Author("Тарас", "Шевченко", 1814);

        repo.add(new Book("Захар Беркут", List.of(franko), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Лісова пісня", List.of(ukrainka), "2222222222222", BookStatus.CHECKED_OUT));
        repo.add(new Book("Кобзар", List.of(shevchenko), "3333333333333", BookStatus.AVAILABLE));
        repo.add(new Book("Перехресні стежки", List.of(franko), "4444444444444", BookStatus.RESERVED));
        repo.add(new Book("Камінний хрест", List.of(franko), "5555555555555", BookStatus.AVAILABLE));

        // FILTER
        System.out.println("\n--- Filter: Доступні книги ---");
        repo.findAvailableBooks()
                .forEach(b -> System.out.println("  • " + b.getTitle()));

        System.out.println("\n--- Filter: Книги Франка ---");
        repo.findByAuthorLastName("Франко")
                .forEach(b -> System.out.println("  • " + b.getTitle()));

        // MAP
        System.out.println("\n--- Map: Всі назви книг ---");
        repo.getAllTitles()
                .forEach(title -> System.out.println("  • " + title));

        // GROUPING
        System.out.println("\n--- Grouping: Книги по статусу ---");
        Map<BookStatus, Long> grouped = repo.countBooksByStatus();
        grouped.forEach((status, count) ->
                System.out.println("  • " + status + ": " + count));

        // REDUCE
        System.out.println("\n--- Reduce: Книга з найбільшою кількістю авторів ---");
        repo.getBookWithMostAuthors()
                .ifPresent(b -> System.out.println("  • " + b.getTitle() +
                        " (" + b.getAuthors().size() + " авторів)"));
    }

    private static void demonstrateFlatMap() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. STREAM API: FLATMAP");
        System.out.println("=".repeat(80));

        BookRepository repo = new BookRepository();
        Author franko = new Author("Іван", "Франко", 1856);
        Author ukrainka = new Author("Леся", "Українка", 1871);
        Author shevchenko = new Author("Тарас", "Шевченко", 1814);
        Author kotsubynsky = new Author("Михайло", "Коцюбинський", 1864);

        repo.add(new Book("Книга 1", List.of(franko, ukrainka), "1111111111111", BookStatus.AVAILABLE));
        repo.add(new Book("Книга 2", List.of(shevchenko), "2222222222222", BookStatus.AVAILABLE));
        repo.add(new Book("Книга 3", List.of(franko, kotsubynsky), "3333333333333", BookStatus.AVAILABLE));

        System.out.println("\n--- FlatMap: Всі автори з усіх книг ---");
        repo.getAllAuthorsFromBooks()
                .forEach(a -> System.out.println("  • " + a.firstName() + " " + a.lastName()));

        System.out.println("\n--- FlatMap: Унікальні прізвища авторів ---");
        repo.getAllAuthorLastNames()
                .forEach(name -> System.out.println("  • " + name));

        System.out.println("\n--- Reduce: Загальна кількість авторів у всіх книгах ---");
        System.out.println("  • Всього авторів (з повтореннями): " + repo.getTotalAuthorsCount());
    }

    private static void demonstrateTerminalOperations() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("5. ТЕРМІНАЛЬНІ ОПЕРАЦІЇ");
        System.out.println("=".repeat(80));

        AuthorRepository repo = new AuthorRepository();
        repo.add(new Author("A", "Test1", 1800));
        repo.add(new Author("B", "Test2", 1850));
        repo.add(new Author("C", "Test3", 1900));

        System.out.println("\n--- collect() ---");
        List<String> names = repo.getAllFullNames();
        System.out.println("Зібрано імен: " + names.size());

        System.out.println("\n--- forEach() ---");
        System.out.println("Виведення через forEach:");
        repo.getAll().stream()
                .forEach(a -> System.out.println("  • " + a.firstName()));

        System.out.println("\n--- reduce() ---");
        int totalAge = repo.getTotalAge();
        System.out.println("Сума віків: " + totalAge);

        System.out.println("\n--- count() ---");
        long count = repo.getAll().stream().count();
        System.out.println("Кількість авторів: " + count);

        System.out.println("\n--- findFirst() ---");
        repo.getAll().stream()
                .filter(a -> a.birthYear() > 1850)
                .findFirst()
                .ifPresent(a -> System.out.println("Перший автор після 1850: " + a));

        System.out.println("\n--- anyMatch() ---");
        boolean hasOld = repo.getAll().stream()
                .anyMatch(a -> a.birthYear() < 1800);
        System.out.println("Є автори до 1800? " + hasOld);
    }

    private static void demonstratePerformanceComparison() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("6. ПОРІВНЯННЯ ПРОДУКТИВНОСТІ: STREAM vs PARALLEL STREAM");
        System.out.println("=".repeat(80));

        // Створюємо великий репозиторій для тестування
        AuthorRepository repo = new AuthorRepository();
        for (int i = 0; i < 10000; i++) {
            repo.add(new Author("Author" + i, "LastName" + i, 1800 + (i % 200)));
        }

        System.out.println("\nДодано " + repo.size() + " авторів для тестування\n");

        // Тест 1: Filter + Count
        System.out.println("--- Тест 1: Filter + Count ---");

        long start = System.nanoTime();
        long countStream = repo.countOlderThan(1900);
        long end = System.nanoTime();
        long timeStream = end - start;

        start = System.nanoTime();
        long countParallel = repo.countOlderThanParallel(1900);
        end = System.nanoTime();
        long timeParallel = end - start;

        System.out.println("Stream:         " + countStream + " результатів, час: " + timeStream / 1_000 + " мкс");
        System.out.println("ParallelStream: " + countParallel + " результатів, час: " + timeParallel / 1_000 + " мкс");
        System.out.println("Прискорення: " + String.format("%.2fx", (double)timeStream / timeParallel));

        // Тест 2: Readers
        ReaderRepository readerRepo = new ReaderRepository();
        for (int i = 0; i < 10000; i++) {
            readerRepo.add(new Reader("Reader" + i, "Last" + i, 100 + i));
        }

        System.out.println("\n--- Тест 2: Readers Filter + Count ---");

        start = System.nanoTime();
        long countReaderStream = readerRepo.countWithIdGreaterThan(5000);
        end = System.nanoTime();
        timeStream = end - start;

        start = System.nanoTime();
        long countReaderParallel = readerRepo.countWithIdGreaterThanParallel(5000);
        end = System.nanoTime();
        timeParallel = end - start;

        System.out.println("Stream:         " + countReaderStream + " результатів, час: " + timeStream / 1_000 + " мкс");
        System.out.println("ParallelStream: " + countReaderParallel + " результатів, час: " + timeParallel / 1_000 + " мкс");
        System.out.println("Прискорення: " + String.format("%.2fx", (double)timeStream / timeParallel));

        System.out.println("\n✓ ParallelStream може бути швидшим на великих колекціях!");
    }
}
