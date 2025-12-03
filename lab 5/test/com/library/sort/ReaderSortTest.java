package com.library;

import com.library.model.Reader;
import com.library.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестування сортування читачів")
class ReaderSortTest {

    private ReaderRepository repo;

    @BeforeEach
    void setUp() {
        repo = new ReaderRepository();
    }

    @Test
    @DisplayName("Сортування за ID")
    void testSortById() {
        repo.add(new Reader("A", "B", 150));
        repo.add(new Reader("C", "D", 120));
        repo.add(new Reader("E", "F", 180));

        List<Reader> sorted = repo.sortById();

        assertEquals(3, sorted.size());
        assertEquals(120, sorted.get(0).readerId());
        assertEquals(150, sorted.get(1).readerId());
        assertEquals(180, sorted.get(2).readerId());
    }

    @Test
    @DisplayName("Сортування за прізвищем")
    void testSortByLastName() {
        repo.add(new Reader("X", "Яворський", 1));
        repo.add(new Reader("Y", "Антонович", 2));
        repo.add(new Reader("Z", "Коваль", 3));

        List<Reader> sorted = repo.sortByLastName();

        assertEquals(3, sorted.size());
        assertEquals("Антонович", sorted.get(0).lastName());
        assertEquals("Коваль", sorted.get(1).lastName());
        assertEquals("Яворський", sorted.get(2).lastName());
    }

    @Test
    @DisplayName("Реалізація Comparable")
    void testComparable() {
        Reader r1 = new Reader("A", "B", 100);
        Reader r2 = new Reader("C", "D", 200);
        Reader r3 = new Reader("E", "F", 150);

        assertTrue(r1.compareTo(r2) < 0, "100 < 200");
        assertTrue(r2.compareTo(r1) > 0, "200 > 100");
        assertTrue(r1.compareTo(r3) < 0, "100 < 150");
        assertTrue(r3.compareTo(r2) < 0, "150 < 200");
    }

    @Test
    @DisplayName("Сортування за Identity (висхідне/спадне)")
    void testSortByIdentity() {
        repo.add(new Reader("A", "T", 150));
        repo.add(new Reader("B", "T", 120));
        repo.add(new Reader("C", "T", 180));

        List<Reader> asc = repo.sortByIdentity("asc");
        assertEquals(3, asc.size());
        assertEquals(120, asc.get(0).readerId(), "Перший у висхідному порядку");
        assertEquals(150, asc.get(1).readerId());
        assertEquals(180, asc.get(2).readerId(), "Останній у висхідному порядку");

        List<Reader> desc = repo.sortByIdentity("desc");
        assertEquals(3, desc.size());
        assertEquals(180, desc.get(0).readerId(), "Перший у спадному порядку");
        assertEquals(150, desc.get(1).readerId());
        assertEquals(120, desc.get(2).readerId(), "Останній у спадному порядку");
    }
}
