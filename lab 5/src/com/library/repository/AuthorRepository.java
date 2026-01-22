package com.library.repository;

import com.library.model.Author;
import com.library.comparators.AuthorComparators;
import com.library.util.Logger;
import java.util.*;
import java.util.stream.Collectors;

public class AuthorRepository extends GenericRepository<Author> {
    private final Logger logger = Logger.getInstance();

    public AuthorRepository() {
        super(author -> author.firstName() + " " + author.lastName());
        logger.info("Створено AuthorRepository");
    }

    // ============ Stream API: FILTER ============

    public List<Author> findByLastName(String lastName) {
        logger.info("Stream: Пошук авторів за прізвищем: " + lastName);
        return getAll().stream()
                .filter(a -> a.lastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Author> findByBirthYear(int year) {
        logger.info("Stream: Пошук авторів за роком народження: " + year);
        return getAll().stream()
                .filter(a -> a.birthYear() == year)
                .collect(Collectors.toList());
    }

    public List<Author> findByBirthYearRange(int startYear, int endYear) {
        logger.info(String.format("Stream: Пошук авторів у діапазоні років %d-%d", startYear, endYear));
        return getAll().stream()
                .filter(a -> a.birthYear() >= startYear && a.birthYear() <= endYear)
                .collect(Collectors.toList());
    }

    public List<Author> findByFirstNameStartsWith(String prefix) {
        logger.info("Stream: Пошук авторів з ім'ям на '" + prefix + "'");
        return getAll().stream()
                .filter(a -> a.firstName().toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    // ============ Stream API: MAP ============

    public List<String> getAllFullNames() {
        logger.info("Stream: Отримання всіх повних імен");
        return getAll().stream()
                .map(a -> a.firstName() + " " + a.lastName())
                .collect(Collectors.toList());
    }

    public List<String> getAllLastNames() {
        logger.info("Stream: Отримання всіх прізвищ");
        return getAll().stream()
                .map(Author::lastName)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Integer> getAllBirthYears() {
        logger.info("Stream: Отримання всіх років народження");
        return getAll().stream()
                .map(Author::birthYear)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // ============ Stream API: REDUCE ============

    public int getTotalAge() {
        logger.info("Stream: Підрахунок загального віку всіх авторів");
        int currentYear = java.time.LocalDate.now().getYear();
        return getAll().stream()
                .mapToInt(a -> currentYear - a.birthYear())
                .reduce(0, Integer::sum);
    }

    public Optional<Author> getOldestAuthor() {
        logger.info("Stream: Пошук найстарішого автора");
        return getAll().stream()
                .min(Comparator.comparingInt(Author::birthYear));
    }

    public Optional<Author> getYoungestAuthor() {
        logger.info("Stream: Пошук наймолодшого автора");
        return getAll().stream()
                .max(Comparator.comparingInt(Author::birthYear));
    }

    public double getAverageAge() {
        logger.info("Stream: Підрахунок середнього віку авторів");
        int currentYear = java.time.LocalDate.now().getYear();
        return getAll().stream()
                .mapToInt(a -> currentYear - a.birthYear())
                .average()
                .orElse(0.0);
    }

    // ============ Stream API: COUNT ============

    public long countByLastName(String lastName) {
        logger.info("Stream: Підрахунок авторів з прізвищем: " + lastName);
        return getAll().stream()
                .filter(a -> a.lastName().equalsIgnoreCase(lastName))
                .count();
    }

    public long countOlderThan(int year) {
        logger.info("Stream: Підрахунок авторів старших за " + year);
        return getAll().stream()
                .filter(a -> a.birthYear() < year)
                .count();
    }

    // ============ PARALLEL STREAM ============

    public long countOlderThanParallel(int year) {
        logger.info("ParallelStream: Підрахунок авторів старших за " + year);
        return getAll().parallelStream()
                .filter(a -> a.birthYear() < year)
                .count();
    }

    // ============ МЕТОДИ СОРТУВАННЯ ============

    public List<Author> sortByBirthYear() {
        logger.info("Сортування авторів за роком народження");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(AuthorComparators.BY_BIRTH_YEAR);
        return sorted;
    }

    public List<Author> sortByBirthYearDesc() {
        logger.info("Сортування авторів за роком народження (спадання)");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(AuthorComparators.BY_BIRTH_YEAR_DESC);
        return sorted;
    }

    public List<Author> sortByLastName() {
        logger.info("Сортування авторів за прізвищем");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(AuthorComparators.BY_LAST_NAME);
        return sorted;
    }

    public List<Author> sortByFullName() {
        logger.info("Сортування авторів за повним ім'ям");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(AuthorComparators.BY_FULL_NAME);
        return sorted;
    }

    public List<Author> sortNaturally() {
        logger.info("Природне сортування авторів (Comparable)");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(null);
        return sorted;
    }
}
