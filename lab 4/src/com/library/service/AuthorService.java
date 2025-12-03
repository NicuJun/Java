package com.library.service;

import com.library.exceptions.InvalidDataException;
import com.library.model.Author;
import com.library.util.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    private static final Logger logger = Logger.getInstance();
    private static final String AUTHORS_FILE = "data/authors.csv";

    public List<Author> loadAuthors() throws FileNotFoundException, IOException, InvalidDataException {
        logger.info("Початок завантаження авторів з файлу");
        List<Author> authors = new ArrayList<>();
        List<String[]> records;

        try {
            records = FileReaderService.readCSV(AUTHORS_FILE);
        } catch (FileNotFoundException e) {
            logger.error("Файл авторів не знайдено: " + AUTHORS_FILE);
            throw e;
        } catch (IOException e) {
            logger.error("Помилка читання файлу авторів", e);
            throw e;
        }

        int successCount = 0;
        int failCount = 0;

        for (int i = 0; i < records.size(); i++) {
            String[] values = records.get(i);
            try {
                Author author = parseAuthor(values, i + 2);
                authors.add(author);
                successCount++;
                logger.info("Автор створено: " + author.firstName() + " " + author.lastName());
            } catch (InvalidDataException e) {
                failCount++;
                logger.error("Не вдалося створити автора з рядка #" + (i + 2) + ": " + e.getMessage());
            }
        }

        logger.info(String.format("Завантаження завершено: успішно=%d, помилок=%d", successCount, failCount));

        if (authors.isEmpty() && !records.isEmpty()) {
            throw new InvalidDataException(
                    "Не вдалося завантажити жодного автора",
                    InvalidDataException.ErrorCode.INVALID_FORMAT
            );
        }

        return authors;
    }

    private Author parseAuthor(String[] values, int lineNumber) throws InvalidDataException {
        if (values.length < 3) {
            throw new InvalidDataException(
                    "Недостатньо полів у рядку #" + lineNumber + ". Очікується: 3, отримано: " + values.length,
                    InvalidDataException.ErrorCode.MISSING_FIELD
            );
        }

        String firstName = values[0];
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidDataException(
                    "Порожнє ім'я в рядку #" + lineNumber,
                    "firstName", firstName,
                    InvalidDataException.ErrorCode.EMPTY_VALUE
            );
        }

        String lastName = values[1];
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidDataException(
                    "Порожнє прізвище в рядку #" + lineNumber,
                    "lastName", lastName,
                    InvalidDataException.ErrorCode.EMPTY_VALUE
            );
        }

        int birthYear;
        try {
            birthYear = Integer.parseInt(values[2]);
            if (birthYear < 1200 || birthYear > java.time.LocalDate.now().getYear()) {
                throw new InvalidDataException(
                        "Некоректний рік народження в рядку #" + lineNumber,
                        "birthYear", values[2],
                        InvalidDataException.ErrorCode.INVALID_RANGE
                );
            }
        } catch (NumberFormatException e) {
            throw new InvalidDataException(
                    "Невірний формат року в рядку #" + lineNumber,
                    "birthYear", values[2],
                    InvalidDataException.ErrorCode.INVALID_FORMAT
            );
        }

        return new Author(firstName, lastName, birthYear);
    }
}
