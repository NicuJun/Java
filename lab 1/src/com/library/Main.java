package com.library;

import com.library.model.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Author author = new Author("Іван", "Франко", 1856);
        Author a2 = Author.of("Леся", "Українка", 1871);
        Reader reader = new Reader("Марія", "Іваненко", 101);
        Reader reader2 = Reader.of("Степан", "Петренко", 102);

        Book book = Book.of("Перехресні стежки", Arrays.asList(author, a2), "1234567890123");
        Loan loan = new Loan(book, reader, LocalDate.now(), LocalDate.now().plusDays(14));
        Membership mem = new Membership(reader2, LocalDate.of(2024,1,1), LocalDate.of(2024,12,31));

        System.out.println(book);
        System.out.println(author);
        System.out.println(reader);
        System.out.println(loan);
        System.out.println(mem);
    }
}
