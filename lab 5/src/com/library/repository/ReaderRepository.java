package com.library.repository;

import com.library.model.Reader;
import com.library.comparators.ReaderComparators;
import com.library.util.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderRepository extends GenericRepository<Reader> {
    private final Logger logger = Logger.getInstance();

    public ReaderRepository() {
        super(Reader::readerId);
        logger.info("Створено ReaderRepository");
    }

    // Сортування за ID
    public List<Reader> sortById() {
        logger.info("Сортування читачів за ID");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(ReaderComparators.BY_ID);
        return sorted;
    }

    // Сортування за ID (спадання)
    public List<Reader> sortByIdDesc() {
        logger.info("Сортування читачів за ID (спадання)");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(ReaderComparators.BY_ID_DESC);
        return sorted;
    }

    // Сортування за прізвищем
    public List<Reader> sortByLastName() {
        logger.info("Сортування читачів за прізвищем");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(ReaderComparators.BY_LAST_NAME);
        return sorted;
    }

    // Сортування за повним ім'ям
    public List<Reader> sortByFullName() {
        logger.info("Сортування читачів за повним ім'ям");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(ReaderComparators.BY_FULL_NAME);
        return sorted;
    }

    // Природне сортування (Comparable)
    public List<Reader> sortNaturally() {
        logger.info("Природне сортування читачів (за ID)");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(null);
        return sorted;
    }

    // Методи пошуку
    public Reader findByReaderId(int id) {
        logger.info("Пошук читача за ID: " + id);
        return findByIdentity(id);
    }

    public List<Reader> findByLastName(String lastName) {
        logger.info("Пошук читачів за прізвищем: " + lastName);
        return getAll().stream()
                .filter(r -> r.lastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }
}
