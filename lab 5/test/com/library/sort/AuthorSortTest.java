package com.library;

import com.library.model.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестування сортування авторів")
class AuthorSortTest {

    private AuthorRepository repo;

    @BeforeEach
    void setUp() {
        repo = new AuthorRepository();
    }

    @Test
    @DisplayName("Природне сортування")
    void testNaturalSort() {
        repo.add(new Author("Леся", "Українка", 1871));
        repo.add(new Author("Іван", "Франко", 1856));
        repo.add(new Author("Тарас", "Шевченко", 1814));

        List<Author> sorted = repo.sortNaturally();

        assertEquals(3, sorted.size());
        assertEquals("Українка", sorted.get(0).lastName(), "Перша має бути Українка (У)");
        assertEquals("Франко", sorted.get(1).lastName(), "Другий має бути Франко (Ф)");
        assertEquals("Шевченко", sorted.get(2).lastName(), "Третій має бути Шевченко (Ш)");
    }

    @Test
    @DisplayName("Сортування за роком народження")
    void testSortByBirthYear() {
        repo.add(new Author("A", "B", 1871));
        repo.add(new Author("C", "D", 1814));
        repo.add(new Author("E", "F", 1856));

        List<Author> sorted = repo.sortByBirthYear();

        assertEquals(3, sorted.size());
        assertEquals(1814, sorted.get(0).birthYear(), "Найстаріший спочатку");
        assertEquals(1856, sorted.get(1).birthYear());
        assertEquals(1871, sorted.get(2).birthYear());
    }

    @Test
    @DisplayName("Сортування за прізвищем")
    void testSortByLastName() {
        repo.add(new Author("X", "Яворський", 1900));
        repo.add(new Author("Y", "Антонович", 1850));
        repo.add(new Author("Z", "Коваль", 1875));

        List<Author> sorted = repo.sortByLastName();

        assertEquals(3, sorted.size());
        assertEquals("Антонович", sorted.get(0).lastName());
        assertEquals("Коваль", sorted.get(1).lastName());
        assertEquals("Яворський", sorted.get(2).lastName());
    }

    @Test
    @DisplayName("Реалізація Comparable")
    void testComparableImplementation() {
        Author a1 = new Author("Іван", "Франко", 1856);
        Author a2 = new Author("Леся", "Українка", 1871);
        Author a3 = new Author("Іван", "Котляревський", 1769);

        assertTrue(a3.compareTo(a1) < 0, "Котляревський < Франко (К < Ф)");
        assertTrue(a2.compareTo(a1) < 0, "Українка < Франко (У < Ф)");
        assertTrue(a3.compareTo(a2) < 0, "Котляревський < Українка (К < У)");
        assertTrue(a1.compareTo(a2) > 0, "Франко > Українка (Ф > У)");
    }
}
