package com.library.repository;

import com.library.model.Reader;
import com.library.comparators.ReaderComparators;
import com.library.util.Logger;
import java.util.*;
import java.util.stream.Collectors;

public class ReaderRepository extends GenericRepository<Reader> {
    private final Logger logger = Logger.getInstance();

    public ReaderRepository() {
        super(Reader::readerId);
        logger.info("Створено ReaderRepository");
    }

    // ============ Stream API: FILTER ============

    public Reader findByReaderId(int id) {
        logger.info("Stream: Пошук читача за ID: " + id);
        return findByIdentity(id);
    }

    public List<Reader> findByLastName(String lastName) {
        logger.info("Stream: Пошук читачів за прізвищем: " + lastName);
        return getAll().stream()
                .filter(r -> r.lastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Reader> findByFirstName(String firstName) {
        logger.info("Stream: Пошук читачів за ім'ям: " + firstName);
        return getAll().stream()
                .filter(r -> r.firstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    public List<Reader> findByIdRange(int minId, int maxId) {
        logger.info(String.format("Stream: Пошук читачів з ID у діапазоні %d-%d", minId, maxId));
        return getAll().stream()
                .filter(r -> r.readerId() >= minId && r.readerId() <= maxId)
                .collect(Collectors.toList());
    }

    public Optional<Reader> findFirstByLastName(String lastName) {
        logger.info("Stream: Пошук першого читача з прізвищем: " + lastName);
        return getAll().stream()
                .filter(r -> r.lastName().equalsIgnoreCase(lastName))
                .findFirst();
    }

    // ============ Stream API: MAP ============

    public List<String> getAllFullNames() {
        logger.info("Stream: Отримання всіх повних імен читачів");
        return getAll().stream()
                .map(r -> r.firstName() + " " + r.lastName())
                .collect(Collectors.toList());
    }

    public List<Integer> getAllIds() {
        logger.info("Stream: Отримання всіх ID читачів");
        return getAll().stream()
                .map(Reader::readerId)
                .sorted()
                .collect(Collectors.toList());
    }

    public Set<String> getUniqueLastNames() {
        logger.info("Stream: Отримання унікальних прізвищ");
        return getAll().stream()
                .map(Reader::lastName)
                .collect(Collectors.toSet());
    }

    // ============ Stream API: REDUCE ============

    public int getTotalIds() {
        logger.info("Stream: Підрахунок суми всіх ID");
        return getAll().stream()
                .mapToInt(Reader::readerId)
                .reduce(0, Integer::sum);
    }

    public Optional<Reader> getReaderWithMaxId() {
        logger.info("Stream: Пошук читача з максимальним ID");
        return getAll().stream()
                .max(Comparator.comparingInt(Reader::readerId));
    }

    public Optional<Reader> getReaderWithMinId() {
        logger.info("Stream: Пошук читача з мінімальним ID");
        return getAll().stream()
                .min(Comparator.comparingInt(Reader::readerId));
    }

    public double getAverageId() {
        logger.info("Stream: Підрахунок середнього ID");
        return getAll().stream()
                .mapToInt(Reader::readerId)
                .average()
                .orElse(0.0);
    }

    // ============ Stream API: COUNT ============

    public long countByLastName(String lastName) {
        logger.info("Stream: Підрахунок читачів з прізвищем: " + lastName);
        return getAll().stream()
                .filter(r -> r.lastName().equalsIgnoreCase(lastName))
                .count();
    }

    public long countWithIdGreaterThan(int id) {
        logger.info("Stream: Підрахунок читачів з ID > " + id);
        return getAll().stream()
                .filter(r -> r.readerId() > id)
                .count();
    }

    // ============ PARALLEL STREAM ============

    public long countWithIdGreaterThanParallel(int id) {
        logger.info("ParallelStream: Підрахунок читачів з ID > " + id);
        return getAll().parallelStream()
                .filter(r -> r.readerId() > id)
                .count();
    }

    // ============ forEach ============

    public void printAllReaders() {
        logger.info("Stream: Виведення всіх читачів");
        getAll().stream()
                .forEach(r -> System.out.println("  • " + r));
    }

    // ============ МЕТОДИ СОРТУВАННЯ ============

    public List<Reader> sortById() {
        logger.info("Сортування читачів за ID");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(ReaderComparators.BY_ID);
        return sorted;
    }

    public List<Reader> sortByIdDesc() {
        logger.info("Сортування читачів за ID (спадання)");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(ReaderComparators.BY_ID_DESC);
        return sorted;
    }

    public List<Reader> sortByLastName() {
        logger.info("Сортування читачів за прізвищем");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(ReaderComparators.BY_LAST_NAME);
        return sorted;
    }

    public List<Reader> sortByFullName() {
        logger.info("Сортування читачів за повним ім'ям");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(ReaderComparators.BY_FULL_NAME);
        return sorted;
    }

    public List<Reader> sortNaturally() {
        logger.info("Природне сортування читачів (за ID)");
        List<Reader> sorted = new ArrayList<>(getAll());
        sorted.sort(null);
        return sorted;
    }
}
