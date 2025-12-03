package com.library.repository;

import com.library.model.Author;
import com.library.comparators.AuthorComparators;
import com.library.util.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorRepository extends GenericRepository<Author> {
    private final Logger logger = Logger.getInstance();

    public AuthorRepository() {
        super(author -> author.firstName() + " " + author.lastName());
        logger.info("Створено AuthorRepository");
    }

    // Сортування за роком народження
    public List<Author> sortByBirthYear() {
        logger.info("Сортування авторів за роком народження");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(AuthorComparators.BY_BIRTH_YEAR);
        return sorted;
    }

    // Сортування за роком (спадання)
    public List<Author> sortByBirthYearDesc() {
        logger.info("Сортування авторів за роком народження (спадання)");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(AuthorComparators.BY_BIRTH_YEAR_DESC);
        return sorted;
    }

    // Сортування за прізвищем
    public List<Author> sortByLastName() {
        logger.info("Сортування авторів за прізвищем");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(AuthorComparators.BY_LAST_NAME);
        return sorted;
    }

    // Сортування за повним ім'ям
    public List<Author> sortByFullName() {
        logger.info("Сортування авторів за повним ім'ям");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(AuthorComparators.BY_FULL_NAME);
        return sorted;
    }

    // Сортування з використанням Comparable
    public List<Author> sortNaturally() {
        logger.info("Природне сортування авторів (Comparable)");
        List<Author> sorted = new ArrayList<>(getAll());
        sorted.sort(null); // використовує compareTo
        return sorted;
    }

    // Пошук методи з попередньої ЛР
    public List<Author> findByLastName(String lastName) {
        logger.info("Пошук авторів за прізвищем: " + lastName);
        return getAll().stream()
                .filter(a -> a.lastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Author> findByBirthYear(int year) {
        logger.info("Пошук авторів за роком народження: " + year);
        return getAll().stream()
                .filter(a -> a.birthYear() == year)
                .collect(Collectors.toList());
    }
}
