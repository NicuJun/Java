package com.library.repository;

import com.library.model.Book;
import com.library.enums.BookStatus;
import com.library.util.Logger;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository extends GenericRepository<Book> {
    private final Logger logger = Logger.getInstance();

    public BookRepository() {
        super(Book::getIsbn);
        logger.info("Створено BookRepository");
    }

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
