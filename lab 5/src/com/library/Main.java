package com.library;

import com.library.model.*;
import com.library.enums.*;
import com.library.repository.*;
import com.library.comparators.*;
import com.library.util.Logger;
import java.util.List;

public class Main {
    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        logger.info("=".repeat(80));
        logger.info("ЗАПУСК LIBRARY SYSTEM - ЛР 5: Сортування");
        logger.info("=".repeat(80));

        demonstrateAuthorSorting();
        demonstrateReaderSorting();
        demonstrateBookSorting();
        demonstrateSortByIdentity();
        demonstrateLambdaSorting();

        logger.info("=".repeat(80));
        logger.info("✓ Програма завершена успішно!");
        logger.info("=".repeat(80));
    }

    private static void demonstrateAuthorSorting() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. СОРТУВАННЯ АВТОРІВ");
        System.out.println("=".repeat(80));

        AuthorRepository repo = new AuthorRepository();
        repo.add(new Author("Іван", "Франко", 1856));
        repo.add(new Author("Леся", "Українка", 1871));
        repo.add(new Author("Тарас", "Шевченко", 1814));
        repo.add(new Author("Михайло", "Коцюбинський", 1864));
        repo.add(new Author("Іван", "Котляревський", 1769));

        System.out.println("\n--- Природне сортування (Comparable: за прізвищем) ---");
        repo.sortNaturally().forEach(System.out::println);

        System.out.println("\n--- За роком народження (зростання) ---");
        repo.sortByBirthYear().forEach(System.out::println);

        System.out.println("\n--- За роком народження (спадання) ---");
        repo.sortByBirthYearDesc().forEach(System.out::println);

        System.out.println("\n--- За прізвищем ---");
        repo.sortByLastName().forEach(System.out::println);
    }

    private static void demonstrateReaderSorting() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. СОРТУВАННЯ ЧИТАЧІВ");
        System.out.println("=".repeat(80));

        ReaderRepository repo = new ReaderRepository();
        repo.add(new Reader("Марія", "Іваненко", 105));
        repo.add(new Reader("Степан", "Петренко", 102));
        repo.add(new Reader("Ганна", "Сидоренко", 108));
        repo.add(new Reader("Олександр", "Коваль", 101));
        repo.add(new Reader("Анна", "Петренко", 110));

        System.out.println("\n--- Природне сортування (Comparable: за ID) ---");
        repo.sortNaturally().forEach(System.out::println);

        System.out.println("\n--- За ID (спадання) ---");
        repo.sortByIdDesc().forEach(System.out::println);

        System.out.println("\n--- За прізвищем ---");
        repo.sortByLastName().forEach(System.out::println);

        System.out.println("\n--- За повним ім'ям ---");
        repo.sortByFullName().forEach(System.out::println);
    }

    private static void demonstrateBookSorting() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. СОРТУВАННЯ КНИГ");
        System.out.println("=".repeat(80));

        BookRepository repo = new BookRepository();
        Author franko = new Author("Іван", "Франко", 1856);
        Author ukrainka = new Author("Леся", "Українка", 1871);

        repo.add(new Book("Захар Беркут", List.of(franko), "3333333333333", BookStatus.AVAILABLE));
        repo.add(new Book("Лісова пісня", List.of(ukrainka), "1111111111111", BookStatus.CHECKED_OUT));
        repo.add(new Book("Перехресні стежки", List.of(franko), "2222222222222", BookStatus.AVAILABLE));
        repo.add(new Book("Камінний хрест", List.of(franko), "5555555555555", BookStatus.RESERVED));
        repo.add(new Book("Бориславські оповідання", List.of(franko), "4444444444444", BookStatus.AVAILABLE));

        System.out.println("\n--- Природне сортування (за назвою) ---");
        repo.sortNaturally().forEach(b -> System.out.println("  • " + b.getTitle()));

        System.out.println("\n--- За ISBN ---");
        repo.sortByIsbn().forEach(b -> System.out.println("  • " + b.getIsbn() + " - " + b.getTitle()));

        System.out.println("\n--- За статусом ---");
        repo.sortByStatus().forEach(b ->
                System.out.println("  • [" + b.getStatus() + "] " + b.getTitle()));

        System.out.println("\n--- Доступні спочатку ---");
        repo.sortAvailableFirst().forEach(b ->
                System.out.println("  • [" + b.getStatus() + "] " + b.getTitle()));
    }

    private static void demonstrateSortByIdentity() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. СОРТУВАННЯ ЗА IDENTITY (GenericRepository)");
        System.out.println("=".repeat(80));

        ReaderRepository repo = new ReaderRepository();
        repo.add(new Reader("A", "Test", 150));
        repo.add(new Reader("B", "Test", 120));
        repo.add(new Reader("C", "Test", 180));
        repo.add(new Reader("D", "Test", 110));

        System.out.println("\n--- Identity (ID) - зростання ---");
        repo.sortByIdentity("asc").forEach(System.out::println);

        System.out.println("\n--- Identity (ID) - спадання ---");
        repo.sortByIdentity("desc").forEach(System.out::println);
    }

    private static void demonstrateLambdaSorting() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("5. ЛЯМБДА-ВИРАЗИ ТА METHOD REFERENCES");
        System.out.println("=".repeat(80));

        AuthorRepository repo = new AuthorRepository();
        repo.add(new Author("Іван", "Франко", 1856));
        repo.add(new Author("Леся", "Українка", 1871));
        repo.add(new Author("Т", "А", 1900));

        System.out.println("\n--- Лямбда: за довжиною прізвища ---");
        List<Author> byLength = new java.util.ArrayList<>(repo.getAll());
        byLength.sort((a1, a2) -> Integer.compare(
                a1.lastName().length(),
                a2.lastName().length()
        ));
        byLength.forEach(a -> System.out.println("  • " + a.lastName() + " (довжина: " + a.lastName().length() + ")"));

        System.out.println("\n--- Method reference: за ім'ям ---");
        List<Author> byFirstName = new java.util.ArrayList<>(repo.getAll());
        byFirstName.sort(java.util.Comparator.comparing(Author::firstName));
        byFirstName.forEach(System.out::println);

        System.out.println("\n--- Комбінований Comparator ---");
        List<Author> combined = new java.util.ArrayList<>(repo.getAll());
        combined.sort(
                java.util.Comparator.comparing(Author::birthYear)
                        .thenComparing(Author::lastName)
        );
        combined.forEach(System.out::println);
    }
}
