package com.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class Membership {
    private Reader reader;
    private LocalDate startDate;
    private LocalDate endDate;

    public Membership(Reader reader, LocalDate startDate, LocalDate endDate) {
        setReader(reader);
        setStartDate(startDate);
        setEndDate(endDate);
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

    @Override public String toString() {
        return "Membership{" + "reader=" + reader.getReaderId() + ", " + startDate + " - " + endDate + '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Membership)) return false;
        Membership m = (Membership) o;
        return Objects.equals(reader, m.reader) &&
                Objects.equals(startDate, m.startDate) && Objects.equals(endDate, m.endDate);
    }
    @Override public int hashCode() { return Objects.hash(reader, startDate, endDate); }
}
