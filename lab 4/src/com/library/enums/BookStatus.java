package com.library.enums;

public enum BookStatus {
    AVAILABLE("Доступна"),
    CHECKED_OUT("Видана"),
    RESERVED("Зарезервована"),
    LOST("Втрачена");

    private final String label;
    BookStatus(String label) { this.label = label; }
    public String getLabel() { return label; }
}
