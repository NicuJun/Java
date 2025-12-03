package com.library;

import com.library.model.Author;
import com.library.repository.GenericRepository;

public class GenericRepositoryTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ GenericRepository ===\n");

        testAddAndFind();
        testDuplicates();
        testRemove();
        testSize();
        testClear();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testAddAndFind() {
        System.out.println("Тест 1: Додавання та пошук");
        GenericRepository<Author> repo = new GenericRepository<>(
                a -> a.firstName() + " " + a.lastName()
        );

        Author author = new Author("Іван", "Франко", 1856);
        repo.add(author);

        Author found = repo.findByIdentity("Іван Франко");
        assert found == author : "Не знайдено автора";
        assert repo.size() == 1 : "Неправильний розмір";

        System.out.println("✓ Тест пройдено\n");
    }

    static void testDuplicates() {
        System.out.println("Тест 2: Обробка дублікатів");
        GenericRepository<Author> repo = new GenericRepository<>(
                a -> a.firstName() + " " + a.lastName()
        );

        Author a1 = new Author("Тарас", "Шевченко", 1814);
        Author a2 = new Author("Тарас", "Шевченко", 1900);

        boolean added1 = repo.add(a1);
        boolean added2 = repo.add(a2);

        assert added1 : "Перший має додатися";
        assert !added2 : "Другий не має додатися (дублікат)";
        assert repo.size() == 1 : "Має бути лише 1 елемент";

        System.out.println("✓ Тест пройдено\n");
    }

    static void testRemove() {
        System.out.println("Тест 3: Видалення");
        GenericRepository<Author> repo = new GenericRepository<>(
                a -> a.firstName() + " " + a.lastName()
        );

        Author author = new Author("Леся", "Українка", 1871);
        repo.add(author);

        boolean removed = repo.removeByIdentity("Леся Українка");
        assert removed : "Має бути видалено";
        assert repo.size() == 0 : "Репозиторій має бути порожнім";

        System.out.println("✓ Тест пройдено\n");
    }

    static void testSize() {
        System.out.println("Тест 4: Розмір");
        GenericRepository<Author> repo = new GenericRepository<>(
                a -> a.firstName() + " " + a.lastName()
        );

        assert repo.size() == 0 : "Спочатку має бути 0";

        repo.add(new Author("A", "B", 1800));
        repo.add(new Author("C", "D", 1850));
        assert repo.size() == 2 : "Має бути 2 елементи";

        System.out.println("✓ Тест пройдено\n");
    }

    static void testClear() {
        System.out.println("Тест 5: Очищення");
        GenericRepository<Author> repo = new GenericRepository<>(
                a -> a.firstName() + " " + a.lastName()
        );

        repo.add(new Author("Test1", "Test1", 1900));
        repo.add(new Author("Test2", "Test2", 1900));

        assert repo.size() == 2 : "Має бути 2 елементи";

        repo.clear();
        assert repo.size() == 0 : "Після clear має бути 0";

        System.out.println("✓ Тест пройдено\n");
    }
}
