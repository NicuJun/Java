package com.library;

import com.library.exceptions.InvalidDataException;
import com.library.model.Author;
import com.library.model.Reader;
import com.library.service.AuthorService;
import com.library.service.ReaderService;
import com.library.util.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {
    private static final Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        logger.info("=".repeat(80));
        logger.info("ЗАПУСК LIBRARY SYSTEM - ЛР 3: Обробка виключень");
        logger.info("=".repeat(80));

        try {
            demonstrateAuthorLoading();
            demonstrateReaderLoading();
            demonstrateErrorHandling();
            demonstrateMultiCatch();

            logger.info("=".repeat(80));
            logger.info("✓ Програма завершена успішно!");
            logger.info("=".repeat(80));

        } catch (Exception e) {
            logger.error("Критична помилка в main: " + e.getMessage(), e);
        }
    }

    private static void demonstrateAuthorLoading() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. ЗАВАНТАЖЕННЯ АВТОРІВ З ФАЙЛУ");
        System.out.println("=".repeat(80));

        AuthorService authorService = new AuthorService();

        try {
            logger.info("Спроба завантаження авторів...");
            List<Author> authors = authorService.loadAuthors();

            System.out.println("\n✓ Успішно завантажено " + authors.size() + " авторів:");
            authors.forEach(a -> System.out.println("  • " + a));

        } catch (FileNotFoundException e) {
            logger.error("Файл не знайдено", e);
            System.err.println("❌ ПОМИЛКА: Файл не знайдено - " + e.getMessage());
        } catch (InvalidDataException e) {
            logger.error("Невалідні дані", e);
            System.err.println("❌ ПОМИЛКА: " + e.getDetailedMessage());
        } catch (IOException e) {
            logger.error("Помилка вводу/виводу", e);
            System.err.println("❌ ПОМИЛКА: Проблема з читанням файлу - " + e.getMessage());
        } finally {
            logger.info("Завершення завантаження авторів");
            System.out.println("\n[FINALLY] Блок finally виконано");
        }
    }

    private static void demonstrateReaderLoading() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. ЗАВАНТАЖЕННЯ ЧИТАЧІВ З ФАЙЛУ");
        System.out.println("=".repeat(80));

        ReaderService readerService = new ReaderService();

        try {
            logger.info("Спроба завантаження читачів...");
            List<Reader> readers = readerService.loadReaders();

            System.out.println("\n✓ Успішно завантажено " + readers.size() + " читачів:");
            readers.forEach(r -> System.out.println("  • " + r));

        } catch (FileNotFoundException | InvalidDataException e) {
            // Multi-catch
            logger.error("Помилка завантаження читачів", e);
            System.err.println("❌ ПОМИЛКА: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Помилка IO", e);
            System.err.println("❌ ПОМИЛКА: " + e.getMessage());
        } finally {
            logger.info("Завершення завантаження читачів");
        }
    }

    private static void demonstrateErrorHandling() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. ДЕМОНСТРАЦІЯ ОБРОБКИ ПОМИЛОК");
        System.out.println("=".repeat(80));

        // Тест: Власне виключення
        System.out.println("\n--- Тест: Власне виключення ---");
        try {
            logger.info("Тестування InvalidDataException");
            throw new InvalidDataException(
                    "Тестова помилка валідації",
                    "testField",
                    "invalidValue",
                    InvalidDataException.ErrorCode.INVALID_FORMAT
            );
        } catch (InvalidDataException e) {
            logger.warning("Власне виключення перехоплено");
            System.out.println("✓ Власне виключення спрацювало:");
            System.out.println("  " + e.getDetailedMessage());
        }
    }

    private static void demonstrateMultiCatch() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. ДЕМОНСТРАЦІЯ MULTI-CATCH");
        System.out.println("=".repeat(80));

        String[] testFiles = {
                "data/authors.csv",
                "data/nonexistent.csv"
        };

        for (String file : testFiles) {
            System.out.println("\n--- Обробка файлу: " + file + " ---");

            try {
                logger.info("Спроба читання: " + file);
                var data = com.library.service.FileReaderService.readCSV(file);
                System.out.println("✓ Файл прочитано: " + data.size() + " записів");

            } catch (FileNotFoundException | InvalidDataException e) {
                logger.warning("Помилка файлу або даних: " + e.getMessage());
                System.out.println("⚠ Помилка: " + e.getClass().getSimpleName());
            } catch (IOException e) {
                logger.error("Помилка вводу/виводу", e);
                System.err.println("❌ IO помилка: " + e.getMessage());
            }
        }
    }
}
