package com.library;

import com.library.model.*;
import com.library.enums.*;
import com.library.repository.*;
import com.library.util.Logger;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        logger.info("=".repeat(80));
        logger.info("ЗАПУСК LIBRARY SYSTEM - ЛР 4: Generics та Collections");
        logger.info("=".repeat(80));

        demonstrateAuthorRepository();
        demonstrateReaderRepository();
        demonstrateBookRepository();
        demonstrateDuplicateHandling();
        demonstrateGenericFeatures();

        logger.info("=".repeat(80));
        logger.info("✓ Програма завершена успішно!");
        logger.info("=".repeat(80));
    }

    private static void demonstrateAuthorRepository() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. ДЕМОНСТРАЦІЯ AuthorRepository");
        System.out.println("=".repeat(80));

        AuthorRepository authorRepo = new AuthorRepository();

        // Додавання авторів
        Author a1 = new Author("Іван", "Франко", 1856);
        Author a2 = new Author("Леся", "Українка", 1871);
        Author a3 = new Author("Тарас", "Шевченко", 1814);
        Author a4 = new Author("Михайло", "Коцюбинський", 1864);

        authorRepo.add(a1);
        authorRepo.add(a2);
        authorRepo.add(a3);
        authorRepo.add(a4);

        System.out.println("\n✓ Додано " + authorRepo.size() + " авторів:");
        authorRepo.getAll().forEach(a -> System.out.println("  • " + a));

        // Пошук за identity (ім'я + прізвище)
        System.out.println("\n--- Пошук за identity ---");
        Author found = authorRepo.findByIdentity("Тарас Шевченко");
        System.out.println("Знайдено: " + found);

        // Пошук за прізвищем
        System.out.println("\n--- Пошук за прізвищем 'Франко' ---");
        List<Author> franko = authorRepo.findByLastName("Франко");
        franko.forEach(a -> System.out.println("  • " + a));

        // Пошук за роком
        System.out.println("\n--- Автори 1856 року ---");
        authorRepo.findByBirthYear(1856).forEach(a -> System.out.println("  • " + a));
    }

    private static void demonstrateReaderRepository() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. ДЕМОНСТРАЦІЯ ReaderRepository");
        System.out.println("=".repeat(80));

        ReaderRepository readerRepo = new ReaderRepository();

        // Додавання читачів
        Reader r1 = new Reader("Марія", "Іваненко", 101);
        Reader r2 = new Reader("Степан", "Петренко", 102);
        Reader r3 = new Reader("Ганна", "Сидоренко", 103);
        Reader r4 = new Reader("Олександр", "Петренко", 104);

        readerRepo.add(r1);
        readerRepo.add(r2);
        readerRepo.add(r3);
        readerRepo.add(r4);

        System.out.println("\n✓ Додано " + readerRepo.size() + " читачів:");
        readerRepo.getAll().forEach(r -> System.out.println("  • " + r));

        // Пошук за ID
        System.out.println("\n--- Пошук за ID 102 ---");
        Reader foundById = readerRepo.findByReaderId(102);
        System.out.println("Знайдено: " + foundById);

        // Пошук за прізвищем
        System.out.println("\n--- Читачі з прізвищем 'Петренко' ---");
        List<Reader> petrenko = readerRepo.findByLastName("Петренко");
        petrenko.forEach(r -> System.out.println("  • " + r));

        // Видалення
        System.out.println("\n--- Видалення читача з ID 103 ---");
        boolean removed = readerRepo.removeByIdentity(103);
        System.out.println("Видалено: " + removed);
        System.out.println("Залишилось читачів: " + readerRepo.size());
    }

    private static void demonstrateBookRepository() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. ДЕМОНСТРАЦІЯ BookRepository");
        System.out.println("=".repeat(80));

        BookRepository bookRepo = new BookRepository();
        AuthorRepository authorRepo = new AuthorRepository();

        // Створюємо авторів для книг
        Author franko = new Author("Іван", "Франko", 1856);
        Author ukrainka = new Author("Леся", "Українка", 1871);

        // Додаємо книги
        Book b1 = new Book("Перехресні стежки", List.of(franko), "1234567890123", BookStatus.AVAILABLE);
        Book b2 = new Book("Лісова пісня", List.of(ukrainka), "9876543210123", BookStatus.CHECKED_OUT);
        Book b3 = new Book("Захар Беркут", List.of(franko), "1111222233334", BookStatus.AVAILABLE);
        Book b4 = new Book("Камінний хрест", List.of(franko), "5555666677778", BookStatus.RESERVED);

        bookRepo.add(b1);
        bookRepo.add(b2);
        bookRepo.add(b3);
        bookRepo.add(b4);

        System.out.println("\n✓ Додано " + bookRepo.size() + " книг:");
        bookRepo.getAll().forEach(b -> System.out.println("  • " + b));

        // Пошук за ISBN
        System.out.println("\n--- Пошук за ISBN ---");
        Book foundBook = bookRepo.findByIsbn("9876543210123");
        System.out.println("Знайдено: " + foundBook);

        // Пошук за статусом
        System.out.println("\n--- Доступні книги (AVAILABLE) ---");
        List<Book> available = bookRepo.findByStatus(BookStatus.AVAILABLE);
        available.forEach(b -> System.out.println("  • " + b.getTitle()));

        // Пошук за назвою
        System.out.println("\n--- Книги з 'Захар' у назві ---");
        bookRepo.findByTitle("Захар").forEach(b -> System.out.println("  • " + b.getTitle()));
    }

    private static void demonstrateDuplicateHandling() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. ДЕМОНСТРАЦІЯ ОБРОБКИ ДУБЛІКАТІВ");
        System.out.println("=".repeat(80));

        ReaderRepository repo = new ReaderRepository();

        Reader r1 = new Reader("Тест", "Тестович", 999);
        Reader r2 = new Reader("Інше", "Ім'я", 999); // Той самий ID!

        System.out.println("\n--- Додавання першого читача ---");
        boolean added1 = repo.add(r1);
        System.out.println("Додано: " + added1 + " → " + r1);

        System.out.println("\n--- Спроба додати дублікат (той самий ID) ---");
        boolean added2 = repo.add(r2);
        System.out.println("Додано: " + added2 + " (має бути false)");

        System.out.println("\n✓ Репозиторій коректно обробляє дублікати");
        System.out.println("  Всього елементів: " + repo.size());
    }

    private static void demonstrateGenericFeatures() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("5. ДЕМОНСТРАЦІЯ GENERIC-ФУНКЦІОНАЛЬНОСТІ");
        System.out.println("=".repeat(80));

        // Репозиторій з власним extractor
        System.out.println("\n--- Кастомний IdentityExtractor ---");
        GenericRepository<Author> customRepo = new GenericRepository<>(
                author -> author.birthYear() // identity = рік народження
        );

        Author a1 = new Author("Автор1", "Прізвище1", 1900);
        Author a2 = new Author("Автор2", "Прізвище2", 1900); // Той самий рік!

        customRepo.add(a1);
        boolean added = customRepo.add(a2); // Не додасться (дублікат по року)
        System.out.println("Спроба додати автора з тим самим роком: " + added);
        System.out.println("Елементів у репозиторії: " + customRepo.size());

        // Демонстрація contains
        System.out.println("\n--- Метод contains ---");
        ReaderRepository readerRepo = new ReaderRepository();
        readerRepo.add(new Reader("Тест", "Тест", 500));
        System.out.println("Чи містить ID 500? " + readerRepo.contains(500));
        System.out.println("Чи містить ID 999? " + readerRepo.contains(999));

        // Демонстрація clear
        System.out.println("\n--- Метод clear ---");
        System.out.println("До clear: " + readerRepo.size() + " елементів");
        readerRepo.clear();
        System.out.println("Після clear: " + readerRepo.size() + " елементів");
    }
}
