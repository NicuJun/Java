package com.library.service;

import com.library.exceptions.InvalidDataException;
import com.library.model.Reader;
import com.library.util.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderService {
    private static final Logger logger = Logger.getInstance();
    private static final String READERS_FILE = "data/readers.csv";

    public List<Reader> loadReaders() throws FileNotFoundException, IOException, InvalidDataException {
        logger.info("Початок завантаження читачів з файлу");
        List<Reader> readers = new ArrayList<>();
        List<String[]> records;

        try {
            records = FileReaderService.readCSV(READERS_FILE);
        } catch (FileNotFoundException e) {
            logger.error("Файл читачів не знайдено: " + READERS_FILE);
            throw e;
        } catch (IOException e) {
            logger.error("Помилка читання файлу читачів", e);
            throw e;
        }

        int successCount = 0;
        int failCount = 0;

        for (int i = 0; i < records.size(); i++) {
            String[] values = records.get(i);
            try {
                Reader reader = parseReader(values, i + 2);
                readers.add(reader);
                successCount++;
                logger.info("Читач створено: " + reader.firstName() + " " + reader.lastName());
            } catch (InvalidDataException e) {
                failCount++;
                logger.error("Не вдалося створити читача з рядка #" + (i + 2) + ": " + e.getMessage());
            }
        }

        logger.info(String.format("Завантаження завершено: успішно=%d, помилок=%d", successCount, failCount));
        return readers;
    }

    private Reader parseReader(String[] values, int lineNumber) throws InvalidDataException {
        if (values.length < 3) {
            throw new InvalidDataException(
                    "Недостатньо полів у рядку #" + lineNumber,
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

        int readerId;
        try {
            readerId = Integer.parseInt(values[2]);
            if (readerId <= 0) {
                throw new InvalidDataException(
                        "ID має бути додатнім в рядку #" + lineNumber,
                        "readerId", values[2],
                        InvalidDataException.ErrorCode.NEGATIVE_VALUE
                );
            }
        } catch (NumberFormatException e) {
            throw new InvalidDataException(
                    "Невірний формат ID в рядку #" + lineNumber,
                    "readerId", values[2],
                    InvalidDataException.ErrorCode.INVALID_FORMAT
            );
        }

        return new Reader(firstName, lastName, readerId);
    }
}
