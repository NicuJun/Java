package com.library.model;

import com.library.enums.MembershipType;
import java.time.LocalDate;
import java.util.Objects;

public class Membership {
    private Reader reader;
    private LocalDate startDate;
    private LocalDate endDate;
    private MembershipType type;

    public Membership(Reader reader, LocalDate startDate, LocalDate endDate, MembershipType type) {
        setReader(reader);
        setStartDate(startDate);
        setEndDate(endDate);
        setType(type);
    }
    public Reader getReader() { return reader; }

    public void setReader(Reader reader) {
        if (reader == null) throw new IllegalArgumentException("reader не може бути null");
        this.reader = reader;
    }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public MembershipType getType() { return type; }

    public void setType(MembershipType type) {
        if (type == null) throw new IllegalArgumentException("MembershipType не може бути null");
        this.type = type;
    }

    @Override public String toString() {
        return "Membership{" + reader.readerId() + ", " + type + ", " + startDate + " - " + endDate + "}";
    }
}
