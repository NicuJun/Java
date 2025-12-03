package com.library.repository;

import com.library.model.Author;
import com.library.util.Logger;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorRepository extends GenericRepository<Author> {
    private final Logger logger = Logger.getInstance();

    public AuthorRepository() {
        super(author -> author.firstName() + " " + author.lastName());
        logger.info("Створено AuthorRepository");
    }

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
