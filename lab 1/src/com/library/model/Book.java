package com.library.model;

import com.library.util.Utils;
import java.util.List;
import java.util.Objects;

public class Book {
    private String title;
    private List<Author> authors;
    private String isbn;

    public Book(String title, List<Author> authors, String isbn) {
        setTitle(title);
        setAuthors(authors);
        setIsbn(isbn);
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

    @Override public String toString() {
        return String.format("Book{title='%s', authors=%s, isbn='%s'}", title, authors, isbn);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book b = (Book) o;
        return Objects.equals(isbn, b.isbn);
    }
    @Override public int hashCode() { return Objects.hash(isbn); }

    public static Book of(String t, List<Author> a, String isbn) {
        return new Book(t, a, isbn);
    }
}
