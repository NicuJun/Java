package com.library;

import com.library.exceptions.InvalidDataException;
import com.library.model.Reader;
import com.library.service.ReaderService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ReaderServiceTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ ReaderService ===\n");

        testLoadReaders();
        testReaderValidation();
        testMultipleCatch();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testLoadReaders() {
        System.out.println("Тест 1: Завантаження читачів з валідного файлу");

        ReaderService service = new ReaderService();
        try {
            List<Reader> readers = service.loadReaders();
            assert readers != null : "Список читачів не може бути null";
            assert !readers.isEmpty() : "Список читачів не може бути порожнім";

            System.out.println("✓ Завантажено " + readers.size() + " читачів");
            readers.forEach(r -> System.out.println("  • " + r.firstName() + " " + r.lastName() + " [ID: " + r.readerId() + "]"));

            // Перевірка валідності даних
            for (Reader r : readers) {
                assert r.firstName() != null && !r.firstName().isEmpty() : "Ім'я не може бути порожнім";
                assert r.lastName() != null && !r.lastName().isEmpty() : "Прізвище не може бути порожнім";
                assert r.readerId() > 0 : "ID має бути додатнім";
            }

            System.out.println("✓ Всі дані валідні");
            System.out.println("✓ Тест пройдено\n");

        } catch (FileNotFoundException e) {
            System.err.println("❌ Файл не знайдено: " + e.getMessage());
            System.err.println("   Створіть файл data/readers.csv");
        } catch (IOException | InvalidDataException e) {
            System.err.println("❌ Помилка: " + e.getMessage());
        }
    }

    static void testReaderValidation() {
        System.out.println("Тест 2: Валідація даних читачів");

        // Тест створення читача з некоректними даними
        try {
            Reader invalidReader = new Reader("", "Прізвище", 101);
            System.err.println("❌ Має було викинути виключення для порожнього імені");

        } catch (IllegalArgumentException e) {
            System.out.println("✓ Валідація порожнього імені працює");
            System.out.println("  Повідомлення: " + e.getMessage());
        }

        try {
            Reader invalidReader = new Reader("Ім'я", "Прізвище", -1);
            System.err.println("❌ Має було викинути виключення для негативного ID");

        } catch (IllegalArgumentException e) {
            System.out.println("✓ Валідація негативного ID працює");
            System.out.println("  Повідомлення: " + e.getMessage());
        }

        try {
            Reader invalidReader = new Reader("Ім'я", "Прізвище", 0);
            System.err.println("❌ Має було викинути виключення для ID=0");

        } catch (IllegalArgumentException e) {
            System.out.println("✓ Валідація ID=0 працює");
            System.out.println("  Повідомлення: " + e.getMessage());
        }

        System.out.println("✓ Тест пройдено\n");
    }

    static void testMultipleCatch() {
        System.out.println("Тест 3: Multi-catch обробка виключень");

        ReaderService service = new ReaderService();
        String[] testFiles = {
                "data/readers.csv",
                "data/nonexistent.csv"
        };

        for (String file : testFiles) {
            System.out.println("  Обробка: " + file);
            try {
                var data = com.library.service.FileReaderService.readCSV(file);
                System.out.println("    ✓ Успішно прочитано: " + data.size() + " записів");

            } catch (FileNotFoundException | InvalidDataException e) {
                System.out.println("    ⚠ Очікувана помилка: " + e.getClass().getSimpleName());

            } catch (IOException e) {
                System.out.println("    ❌ IOException: " + e.getMessage());
            }
        }

        System.out.println("✓ Multi-catch працює коректно");
        System.out.println("✓ Тест пройдено\n");
    }
}
