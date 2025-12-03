package com.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private Book book;
    private Reader reader;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public Loan(Book book, Reader reader, LocalDate issueDate, LocalDate returnDate) {
        setBook(book);
        setReader(reader);
        setIssueDate(issueDate);
        setReturnDate(returnDate);
    }
    public Book getBook() { return book; }

    public void setBook(Book book) {
        if (book == null) throw new IllegalArgumentException("Book не може бути null");
        this.book = book;
    }

    public Reader getReader() { return reader; }

    public void setReader(Reader reader) {
        if (reader == null) throw new IllegalArgumentException("Reader не може бути null");
        this.reader = reader;
    }

    public LocalDate getIssueDate() { return issueDate; }

    public void setIssueDate(LocalDate issueDate) {
        if (issueDate == null) throw new IllegalArgumentException("Потрібна дата видачі");
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() { return returnDate; }

    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    @Override public String toString() {
        return "Loan{" + "book=" + book.getTitle() + ", reader=" + reader.readerId() +
                ", issueDate=" + issueDate + ", returnDate=" + returnDate + '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan l)) return false;
        return Objects.equals(book, l.book) && Objects.equals(reader, l.reader)
                && Objects.equals(issueDate, l.issueDate);
    }
    @Override public int hashCode() { return Objects.hash(book, reader, issueDate); }
}
