package com.library;

import com.library.model.Reader;
import com.library.repository.ReaderRepository;
import java.util.List;

public class ReaderRepositoryTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ ReaderRepository ===\n");

        testFindByReaderId();
        testFindByLastName();
        testIdentityIsReaderId();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testFindByReaderId() {
        System.out.println("Тест 1: Пошук за ID");
        ReaderRepository repo = new ReaderRepository();

        Reader r = new Reader("Тест", "Тестович", 123);
        repo.add(r);

        Reader found = repo.findByReaderId(123);
        assert found != null : "Має знайтись читач з ID 123";
        assert found == r : "Має повернути той самий об'єкт";

        System.out.println("✓ Тест пройдено\n");
    }

    static void testFindByLastName() {
        System.out.println("Тест 2: Пошук за прізвищем");
        ReaderRepository repo = new ReaderRepository();

        repo.add(new Reader("Іван", "Петренко", 1));
        repo.add(new Reader("Марія", "Петренко", 2));
        repo.add(new Reader("Степан", "Іваненко", 3));

        List<Reader> petrenko = repo.findByLastName("Петренко");
        assert petrenko.size() == 2 : "Має знайтись 2 читачів";

        System.out.println("✓ Знайдено " + petrenko.size() + " читачів");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testIdentityIsReaderId() {
        System.out.println("Тест 3: Identity = readerId");
        ReaderRepository repo = new ReaderRepository();

        Reader r1 = new Reader("A", "B", 100);
        Reader r2 = new Reader("C", "D", 100);

        boolean added1 = repo.add(r1);
        boolean added2 = repo.add(r2);

        assert added1 : "Перший має додатися";
        assert !added2 : "Другий не має додатися (дублікат ID)";

        System.out.println("✓ Identity працює коректно");
        System.out.println("✓ Тест пройдено\n");
    }
}
