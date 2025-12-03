package com.library;

import com.library.model.*;
import com.library.enums.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Author author = new Author("Іван", "Франко", 1856);
        Author a2 = Author.of("Леся", "Українка", 1871);

        Reader reader = new Reader("Марія", "Іваненко", 101);
        Reader reader2 = Reader.of("Степан", "Петренко", 102);

        Book book = Book.of("Перехресні стежки", List.of(author, a2), "1234567890123", BookStatus.AVAILABLE);
        Book book2 = Book.of("Лісова пісня", List.of(a2), "11122333445", BookStatus.RESERVED);
        Loan loan = new Loan(book, reader, LocalDate.now(), LocalDate.now().plusDays(14));
        Membership mem = new Membership(reader2, LocalDate.of(2024,1,1), LocalDate.of(2024,12,31), MembershipType.STANDARD);

        System.out.println(book + " → " + book.statusAsMessage());
        System.out.println(book2 + " → " + book2.statusAsMessage());
        System.out.println(mem);
        System.out.println(loan);

        System.out.println("Author: " + author.firstName() + " " + author.lastName() + " (" + author.birthYear() + ")");
        System.out.println("Reader: " + reader.firstName() + ", ID=" + reader.readerId());
    }
}
