package com.library.repository;

import com.library.model.Book;
import com.library.model.Author;
import com.library.enums.BookStatus;
import com.library.comparators.BookComparators;
import com.library.util.Logger;
import java.util.*;
import java.util.stream.Collectors;

public class BookRepository extends GenericRepository<Book> {
    private final Logger logger = Logger.getInstance();

    public BookRepository() {
        super(Book::getIsbn);
        logger.info("Створено BookRepository");
    }

    // ============ Stream API: FILTER ============

    public Book findByIsbn(String isbn) {
        logger.info("Stream: Пошук книги за ISBN: " + isbn);
        return findByIdentity(isbn);
    }

    public List<Book> findByStatus(BookStatus status) {
        logger.info("Stream: Пошук книг за статусом: " + status);
        return getAll().stream()
                .filter(b -> b.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Book> findByTitle(String title) {
        logger.info("Stream: Пошук книг за назвою: " + title);
        return getAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthorLastName(String lastName) {
        logger.info("Stream: Пошук книг за прізвищем автора: " + lastName);
        return getAll().stream()
                .filter(b -> b.getAuthors().stream()
                        .anyMatch(a -> a.lastName().equalsIgnoreCase(lastName)))
                .collect(Collectors.toList());
    }

    public List<Book> findAvailableBooks() {
        logger.info("Stream: Пошук доступних книг");
        return findByStatus(BookStatus.AVAILABLE);
    }

    public Optional<Book> findFirstByTitle(String title) {
        logger.info("Stream: Пошук першої книги з назвою: " + title);
        return getAll().stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    // ============ Stream API: MAP ============

    public List<String> getAllTitles() {
        logger.info("Stream: Отримання всіх назв книг");
        return getAll().stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    public List<String> getAllIsbns() {
        logger.info("Stream: Отримання всіх ISBN");
        return getAll().stream()
                .map(Book::getIsbn)
                .collect(Collectors.toList());
    }

    public Set<BookStatus> getAllStatuses() {
        logger.info("Stream: Отримання всіх унікальних статусів");
        return getAll().stream()
                .map(Book::getStatus)
                .collect(Collectors.toSet());
    }

    // ============ Stream API: FLATMAP ============

    public List<Author> getAllAuthorsFromBooks() {
        logger.info("Stream (flatMap): Отримання всіх авторів з усіх книг");
        return getAll().stream()
                .flatMap(b -> b.getAuthors().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllAuthorNames() {
        logger.info("Stream (flatMap): Отримання імен всіх авторів");
        return getAll().stream()
                .flatMap(b -> b.getAuthors().stream())
                .map(a -> a.firstName() + " " + a.lastName())
                .distinct()
                .collect(Collectors.toList());
    }

    public Set<String> getAllAuthorLastNames() {
        logger.info("Stream (flatMap): Отримання унікальних прізвищ авторів");
        return getAll().stream()
                .flatMap(b -> b.getAuthors().stream())
                .map(Author::lastName)
                .collect(Collectors.toSet());
    }

    // ============ Stream API: REDUCE ============

    public long getTotalBooksCount() {
        logger.info("Stream: Підрахунок загальної кількості книг");
        return getAll().stream().count();
    }

    public long countByStatus(BookStatus status) {
        logger.info("Stream: Підрахунок книг зі статусом: " + status);
        return getAll().stream()
                .filter(b -> b.getStatus() == status)
                .count();
    }

    public int getTotalAuthorsCount() {
        logger.info("Stream (reduce): Підрахунок загальної кількості авторів у всіх книгах");
        return getAll().stream()
                .mapToInt(b -> b.getAuthors().size())
                .reduce(0, Integer::sum);
    }

    public Optional<Book> getBookWithMostAuthors() {
        logger.info("Stream: Пошук книги з найбільшою кількістю авторів");
        return getAll().stream()
                .max(Comparator.comparingInt(b -> b.getAuthors().size()));
    }

    // ============ Stream API: GROUPING ============

    public Map<BookStatus, List<Book>> groupByStatus() {
        logger.info("Stream: Групування книг за статусом");
        return getAll().stream()
                .collect(Collectors.groupingBy(Book::getStatus));
    }

    public Map<BookStatus, Long> countBooksByStatus() {
        logger.info("Stream: Підрахунок книг по кожному статусу");
        return getAll().stream()
                .collect(Collectors.groupingBy(Book::getStatus, Collectors.counting()));
    }

    // ============ PARALLEL STREAM ============

    public long countAvailableBooksParallel() {
        logger.info("ParallelStream: Підрахунок доступних книг");
        return getAll().parallelStream()
                .filter(b -> b.getStatus() == BookStatus.AVAILABLE)
                .count();
    }

    // ============ forEach ============

    public void printAllBooks() {
        logger.info("Stream: Виведення всіх книг");
        getAll().stream()
                .forEach(b -> System.out.println("  • " + b.getTitle() + " [" + b.getStatus() + "]"));
    }

    // ============ МЕТОДИ СОРТУВАННЯ ============

    public List<Book> sortByTitle() {
        logger.info("Сортування книг за назвою");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(BookComparators.BY_TITLE);
        return sorted;
    }

    public List<Book> sortByIsbn() {
        logger.info("Сортування книг за ISBN");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(BookComparators.BY_ISBN);
        return sorted;
    }

    public List<Book> sortByStatus() {
        logger.info("Сортування книг за статусом");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(BookComparators.BY_STATUS);
        return sorted;
    }

    public List<Book> sortAvailableFirst() {
        logger.info("Сортування: доступні книги спочатку");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(BookComparators.availableFirst());
        return sorted;
    }

    public List<Book> sortNaturally() {
        logger.info("Природне сортування книг (за назвою)");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(null);
        return sorted;
    }
}
