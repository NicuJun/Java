package com.library.repository;

import com.library.model.Book;
import com.library.enums.BookStatus;
import com.library.comparators.BookComparators;
import com.library.util.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository extends GenericRepository<Book> {
    private final Logger logger = Logger.getInstance();

    public BookRepository() {
        super(Book::getIsbn);
        logger.info("Створено BookRepository");
    }

    // Сортування за назвою
    public List<Book> sortByTitle() {
        logger.info("Сортування книг за назвою");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(BookComparators.BY_TITLE);
        return sorted;
    }

    // Сортування за ISBN
    public List<Book> sortByIsbn() {
        logger.info("Сортування книг за ISBN");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(BookComparators.BY_ISBN);
        return sorted;
    }

    // Сортування за статусом
    public List<Book> sortByStatus() {
        logger.info("Сортування книг за статусом");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(BookComparators.BY_STATUS);
        return sorted;
    }

    // Доступні книги спочатку
    public List<Book> sortAvailableFirst() {
        logger.info("Сортування: доступні книги спочатку");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(BookComparators.availableFirst());
        return sorted;
    }

    // Природне сортування
    public List<Book> sortNaturally() {
        logger.info("Природне сортування книг (за назвою)");
        List<Book> sorted = new ArrayList<>(getAll());
        sorted.sort(null);
        return sorted;
    }

    // Методи пошуку
    public Book findByIsbn(String isbn) {
        logger.info("Пошук книги за ISBN: " + isbn);
        return findByIdentity(isbn);
    }

    public List<Book> findByStatus(BookStatus status) {
        logger.info("Пошук книг за статусом: " + status);
        return getAll().stream()
                .filter(b -> b.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Book> findByTitle(String title) {
        logger.info("Пошук книг за назвою: " + title);
        return getAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }
}
