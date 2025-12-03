package com.library.model;

import com.library.enums.BookStatus;
import com.library.util.Utils;
import java.util.List;
import java.util.Objects;

public class Book {
    private String title;
    private List<Author> authors;
    private String isbn;
    private BookStatus status;

    public Book(String title, List<Author> authors, String isbn, BookStatus status) {
        setTitle(title);
        setAuthors(authors);
        setIsbn(isbn);
        setStatus(status == null ? BookStatus.AVAILABLE : status);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { Utils.validateNotEmpty(title, "Назва"); this.title = title; }
    public List<Author> getAuthors() { return authors; }
    public void setAuthors(List<Author> authors) {
        if (authors == null || authors.isEmpty()) throw new IllegalArgumentException("Автори не можуть бути порожніми");
        this.authors = authors;
    }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { Utils.validateISBN(isbn); this.isbn = isbn; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    public String statusAsMessage() {
        return switch (status) {
            case AVAILABLE    -> "Книга в наявності.";
            case CHECKED_OUT  -> "Книга видана читачеві.";
            case RESERVED     -> "Книга зарезервована.";
            case LOST         -> "Книга втрачена.";
        };
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", isbn='" + isbn + '\'' +
                ", status=" + status.getLabel() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book b)) return false;
        return Objects.equals(isbn, b.isbn);
    }
    @Override
    public int hashCode() { return Objects.hash(isbn); }

    public static Book of(String t, List<Author> a, String isbn, BookStatus status) {
        return new Book(t, a, isbn, status);
    }
}
