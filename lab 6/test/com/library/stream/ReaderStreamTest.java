package com.library;

import com.library.model.Reader;
import com.library.repository.ReaderRepository;
import java.util.*;

public class ReaderStreamTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ STREAM API: ЧИТАЧІ ===\n");

        testFilter();
        testMap();
        testReduce();
        testCount();
        testParallelStream();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testFilter() {
        System.out.println("Тест 1: Filter - пошук за прізвищем");
        ReaderRepository repo = new ReaderRepository();

        repo.add(new Reader("Іван", "Петренко", 101));
        repo.add(new Reader("Марія", "Петренко", 102));
        repo.add(new Reader("Степан", "Іваненко", 103));
        repo.add(new Reader("Ганна", "Петренко", 104));

        List<Reader> petrenko = repo.findByLastName("Петренко");
        assert petrenko.size() == 3 : "Має бути 3 читачі з прізвищем Петренко";

        System.out.println("✓ Знайдено " + petrenko.size() + " читачів з прізвищем 'Петренко'");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testMap() {
        System.out.println("Тест 2: Map - отримання ID та імен");
        ReaderRepository repo = new ReaderRepository();

        repo.add(new Reader("Іван", "Петренко", 101));
        repo.add(new Reader("Марія", "Іваненко", 102));
        repo.add(new Reader("Степан", "Коваль", 103));

        List<Integer> ids = repo.getAllIds();
        assert ids.size() == 3 : "Має бути 3 ID";
        assert ids.get(0) == 101 : "Перший ID має бути 101";
        assert ids.get(2) == 103 : "Останній ID має бути 103";

        List<String> fullNames = repo.getAllFullNames();
        assert fullNames.size() == 3 : "Має бути 3 імені";
        assert fullNames.contains("Іван Петренко") : "Має бути 'Іван Петренко'";

        Set<String> lastNames = repo.getUniqueLastNames();
        assert lastNames.size() == 3 : "Має бути 3 унікальних прізвища";

        System.out.println("✓ IDs: " + ids);
        System.out.println("✓ Унікальних прізвищ: " + lastNames.size());
        System.out.println("✓ Тест пройдено\n");
    }

    static void testReduce() {
        System.out.println("Тест 3: Reduce - пошук мін/макс ID та середнього");
        ReaderRepository repo = new ReaderRepository();

        repo.add(new Reader("A", "B", 150));
        repo.add(new Reader("C", "D", 120));
        repo.add(new Reader("E", "F", 180));
        repo.add(new Reader("G", "H", 110));

        var minReader = repo.getReaderWithMinId();
        assert minReader.isPresent() : "Має знайтись читач з мін ID";
        assert minReader.get().readerId() == 110 : "Мін ID має бути 110";

        var maxReader = repo.getReaderWithMaxId();
        assert maxReader.isPresent() : "Має знайтись читач з макс ID";
        assert maxReader.get().readerId() == 180 : "Макс ID має бути 180";

        double avgId = repo.getAverageId();
        assert avgId == 140.0 : "Середній ID має бути 140";

        int totalIds = repo.getTotalIds();
        assert totalIds == 560 : "Сума ID має бути 560";

        System.out.println("✓ Мін ID: " + minReader.get().readerId());
        System.out.println("✓ Макс ID: " + maxReader.get().readerId());
        System.out.println("✓ Середній ID: " + avgId);
        System.out.println("✓ Сума ID: " + totalIds);
        System.out.println("✓ Тест пройдено\n");
    }

    static void testCount() {
        System.out.println("Тест 4: Count - підрахунок за критеріями");
        ReaderRepository repo = new ReaderRepository();

        repo.add(new Reader("A", "Петренко", 100));
        repo.add(new Reader("B", "Петренко", 150));
        repo.add(new Reader("C", "Іваненко", 200));
        repo.add(new Reader("D", "Коваль", 250));

        long countPetrenko = repo.countByLastName("Петренко");
        assert countPetrenko == 2 : "Має бути 2 Петренків";

        long countGreater150 = repo.countWithIdGreaterThan(150);
        assert countGreater150 == 2 : "Має бути 2 читачі з ID > 150";

        System.out.println("✓ Петренків: " + countPetrenko);
        System.out.println("✓ ID > 150: " + countGreater150);
        System.out.println("✓ Тест пройдено\n");
    }

    static void testParallelStream() {
        System.out.println("Тест 5: Parallel Stream");
        ReaderRepository repo = new ReaderRepository();

        // Додаємо багато читачів для тестування
        for (int i = 0; i < 1000; i++) {
            repo.add(new Reader("Reader" + i, "Last" + i, 100 + i));
        }

        long countStream = repo.countWithIdGreaterThan(500);
        long countParallel = repo.countWithIdGreaterThanParallel(500);

        assert countStream == countParallel : "Stream та ParallelStream мають давати однаковий результат";
        assert countStream == 599 : "Має бути 599 читачів з ID > 500";

        System.out.println("✓ Stream результат: " + countStream);
        System.out.println("✓ ParallelStream результат: " + countParallel);
        System.out.println("✓ Результати співпадають!");
        System.out.println("✓ Тест пройдено\n");
    }
}
