package com.library;

import com.library.model.Reader;
import com.library.repository.ReaderRepository;
import java.util.List;

public class ReaderSortTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ СОРТУВАННЯ ЧИТАЧІВ ===\n");

        testSortById();
        testSortByLastName();
        testComparable();
        testSortByIdentity();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testSortById() {
        System.out.println("Тест 1: Сортування за ID");
        ReaderRepository repo = new ReaderRepository();

        repo.add(new Reader("A", "B", 150));
        repo.add(new Reader("C", "D", 120));
        repo.add(new Reader("E", "F", 180));

        List<Reader> sorted = repo.sortById();

        assert sorted.get(0).readerId() == 120;
        assert sorted.get(1).readerId() == 150;
        assert sorted.get(2).readerId() == 180;

        System.out.println("✓ Тест пройдено\n");
    }

    static void testSortByLastName() {
        System.out.println("Тест 2: Сортування за прізвищем");
        ReaderRepository repo = new ReaderRepository();

        repo.add(new Reader("X", "Яворський", 1));
        repo.add(new Reader("Y", "Антонович", 2));
        repo.add(new Reader("Z", "Коваль", 3));

        List<Reader> sorted = repo.sortByLastName();

        assert sorted.get(0).lastName().equals("Антонович");
        assert sorted.get(1).lastName().equals("Коваль");
        assert sorted.get(2).lastName().equals("Яворський");

        System.out.println("✓ Тест пройдено\n");
    }

    static void testComparable() {
        System.out.println("Тест 3: Реалізація Comparable");

        Reader r1 = new Reader("A", "B", 100);
        Reader r2 = new Reader("C", "D", 200);
        Reader r3 = new Reader("E", "F", 150);

        assert r1.compareTo(r2) < 0 : "100 < 200";
        assert r2.compareTo(r1) > 0 : "200 > 100";
        assert r1.compareTo(r3) < 0 : "100 < 150";

        System.out.println("✓ Comparable працює коректно");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testSortByIdentity() {
        System.out.println("Тест 4: Сортування за Identity");
        ReaderRepository repo = new ReaderRepository();

        repo.add(new Reader("A", "T", 150));
        repo.add(new Reader("B", "T", 120));
        repo.add(new Reader("C", "T", 180));

        List<Reader> asc = repo.sortByIdentity("asc");
        assert asc.get(0).readerId() == 120;
        assert asc.get(2).readerId() == 180;

        List<Reader> desc = repo.sortByIdentity("desc");
        assert desc.get(0).readerId() == 180;
        assert desc.get(2).readerId() == 120;

        System.out.println("✓ Тест пройдено\n");
    }
}
