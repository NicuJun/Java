package com.library;

import com.library.model.Author;
import com.library.repository.AuthorRepository;
import java.util.List;

public class AuthorSortTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ СОРТУВАННЯ АВТОРІВ ===\n");

        testNaturalSort();
        testSortByBirthYear();
        testSortByLastName();
        testComparableImplementation();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testNaturalSort() {
        System.out.println("Тест 1: Природне сортування");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("Леся", "Українка", 1871));
        repo.add(new Author("Іван", "Франко", 1856));
        repo.add(new Author("Тарас", "Шевченко", 1814));

        List<Author> sorted = repo.sortNaturally();

        assert sorted.get(0).lastName().equals("Франко") : "Перший має бути Франко";
        assert sorted.get(1).lastName().equals("Українка") : "Другий має бути Українка";
        assert sorted.get(2).lastName().equals("Шевченко") : "Третій має бути Шевченко";

        System.out.println("✓ Тест пройдено\n");
    }

    static void testSortByBirthYear() {
        System.out.println("Тест 2: Сортування за роком");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("A", "B", 1871));
        repo.add(new Author("C", "D", 1814));
        repo.add(new Author("E", "F", 1856));

        List<Author> sorted = repo.sortByBirthYear();

        assert sorted.get(0).birthYear() == 1814 : "Найстаріший спочатку";
        assert sorted.get(1).birthYear() == 1856;
        assert sorted.get(2).birthYear() == 1871;

        System.out.println("✓ Тест пройдено\n");
    }

    static void testSortByLastName() {
        System.out.println("Тест 3: Сортування за прізвищем");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("X", "Яворський", 1900));
        repo.add(new Author("Y", "Антонович", 1850));
        repo.add(new Author("Z", "Коваль", 1875));

        List<Author> sorted = repo.sortByLastName();

        assert sorted.get(0).lastName().equals("Антонович");
        assert sorted.get(1).lastName().equals("Коваль");
        assert sorted.get(2).lastName().equals("Яворський");

        System.out.println("✓ Тест пройдено\n");
    }

    static void testComparableImplementation() {
        System.out.println("Тест 4: Реалізація Comparable");

        Author a1 = new Author("Іван", "Франко", 1856);
        Author a2 = new Author("Леся", "Українка", 1871);
        Author a3 = new Author("Іван", "Котляревський", 1769);

        assert a1.compareTo(a2) < 0 : "Франко < Українка";
        assert a1.compareTo(a3) > 0 : "Франко > Котляревський";

        System.out.println("✓ Comparable працює коректно");
        System.out.println("✓ Тест пройдено\n");
    }
}
