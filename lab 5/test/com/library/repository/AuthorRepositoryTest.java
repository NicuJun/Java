package com.library;

import com.library.model.Author;
import com.library.repository.AuthorRepository;
import java.util.List;

public class AuthorRepositoryTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ AuthorRepository ===\n");

        testFindByLastName();
        testFindByBirthYear();
        testIdentityExtractor();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testFindByLastName() {
        System.out.println("Тест 1: Пошук за прізвищем");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("Іван", "Франко", 1856));
        repo.add(new Author("Петро", "Франко", 1900));
        repo.add(new Author("Леся", "Українка", 1871));

        List<Author> franko = repo.findByLastName("Франко");
        assert franko.size() == 2 : "Має знайтись 2 авторів з прізвищем Франко";

        System.out.println("✓ Знайдено " + franko.size() + " авторів");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testFindByBirthYear() {
        System.out.println("Тест 2: Пошук за роком народження");
        AuthorRepository repo = new AuthorRepository();

        repo.add(new Author("Автор1", "Прізвище1", 1856));
        repo.add(new Author("Автор2", "Прізвище2", 1856));
        repo.add(new Author("Автор3", "Прізвище3", 1900));

        List<Author> year1856 = repo.findByBirthYear(1856);
        assert year1856.size() == 2 : "Має знайтись 2 авторів 1856 року";

        System.out.println("✓ Знайдено " + year1856.size() + " авторів");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testIdentityExtractor() {
        System.out.println("Тест 3: IdentityExtractor (ім'я + прізвище)");
        AuthorRepository repo = new AuthorRepository();

        Author a = new Author("Тест", "Тестович", 2000);
        repo.add(a);

        Author found = repo.findByIdentity("Тест Тестович");
        assert found != null : "Має знайтись за identity";
        assert found == a : "Має повернути той самий об'єкт";

        System.out.println("✓ Identity extractor працює коректно");
        System.out.println("✓ Тест пройдено\n");
    }
}
