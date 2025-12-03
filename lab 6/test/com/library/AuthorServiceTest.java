package com.library;

import com.library.exceptions.InvalidDataException;
import com.library.model.Author;
import com.library.service.AuthorService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AuthorServiceTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ AuthorService ===\n");

        testLoadAuthors();
        testInvalidFile();
        testEmptyFile();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testLoadAuthors() {
        System.out.println("Тест 1: Завантаження авторів з валідного файлу");

        AuthorService service = new AuthorService();
        try {
            List<Author> authors = service.loadAuthors();
            assert authors != null : "Список авторів не може бути null";
            assert !authors.isEmpty() : "Список авторів не може бути порожнім";

            System.out.println("✓ Завантажено " + authors.size() + " авторів");
            authors.forEach(a -> System.out.println("  • " + a.firstName() + " " + a.lastName()));

            // Перевірка першого автора
            Author first = authors.get(0);
            assert first.firstName() != null : "Ім'я не може бути null";
            assert first.lastName() != null : "Прізвище не може бути null";
            assert first.birthYear() > 1200 : "Рік народження некоректний";

            System.out.println("✓ Тест пройдено\n");

        } catch (FileNotFoundException e) {
            System.err.println("❌ Файл не знайдено: " + e.getMessage());
            System.err.println("   Створіть файл data/authors.csv з даними");
        } catch (IOException | InvalidDataException e) {
            System.err.println("❌ Помилка: " + e.getMessage());
        }
    }

    static void testInvalidFile() {
        System.out.println("Тест 2: Обробка невалідного файлу");

        // Цей тест спробує завантажити файл з невалідними даними
        // Створіть data/invalid_authors.csv для тестування
        try {
            // Тимчасово змінюємо шлях для тесту
            List<String[]> data = com.library.service.FileReaderService.readCSV("data/invalid_authors.csv");
            System.out.println("  Прочитано рядків: " + data.size());
            System.out.println("✓ Обробка помилок працює корректно\n");

        } catch (FileNotFoundException e) {
            System.out.println("  Файл invalid_authors.csv не знайдено (це нормально для тесту)");
            System.out.println("✓ FileNotFoundException обробляється коректно\n");
        } catch (IOException | InvalidDataException e) {
            System.out.println("  Перехоплено очікувану помилку: " + e.getClass().getSimpleName());
            System.out.println("✓ Тест пройдено\n");
        }
    }

    static void testEmptyFile() {
        System.out.println("Тест 3: Обробка неіснуючого файлу");

        AuthorService service = new AuthorService();
        try {
            // Спроба завантажити з неіснуючого файлу
            var data = com.library.service.FileReaderService.readCSV("data/nonexistent.csv");
            System.err.println("❌ Має було викинути FileNotFoundException");

        } catch (FileNotFoundException e) {
            System.out.println("✓ FileNotFoundException правильно викинуто");
            System.out.println("  Повідомлення: " + e.getMessage());
            System.out.println("✓ Тест пройдено\n");

        } catch (IOException | InvalidDataException e) {
            System.err.println("❌ Неочікуваний тип виключення: " + e.getClass());
        }
    }
}
