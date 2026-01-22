package com.library;

import com.library.exceptions.InvalidDataException;

public class InvalidDataExceptionTest {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТУВАННЯ InvalidDataException ===\n");

        testConstructor();
        testErrorCodes();
        testDetailedMessage();

        System.out.println("\n=== ВСІ ТЕСТИ ПРОЙДЕНО ===");
    }

    static void testConstructor() {
        System.out.println("Тест 1: Конструктор");
        InvalidDataException ex = new InvalidDataException(
                "Тест", "field", "value",
                InvalidDataException.ErrorCode.EMPTY_VALUE
        );
        assert ex.getFieldName().equals("field");
        assert ex.getInvalidValue().equals("value");
        System.out.println("✓ Тест пройдено\n");
    }

    static void testErrorCodes() {
        System.out.println("Тест 2: Коди помилок");
        for (var code : InvalidDataException.ErrorCode.values()) {
            assert code.getDescription() != null;
            System.out.println("  " + code + ": " + code.getDescription());
        }
        System.out.println("✓ Тест пройдено\n");
    }

    static void testDetailedMessage() {
        System.out.println("Тест 3: Детальне повідомлення");
        InvalidDataException ex = new InvalidDataException(
                "Тест", "field", "val",
                InvalidDataException.ErrorCode.INVALID_FORMAT
        );
        String msg = ex.getDetailedMessage();
        assert msg.contains("field");
        assert msg.contains("val");
        System.out.println("✓ Тест пройдено\n");
    }
}