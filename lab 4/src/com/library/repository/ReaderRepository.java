package com.library.repository;

import com.library.model.Reader;
import com.library.util.Logger;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderRepository extends GenericRepository<Reader> {
    private final Logger logger = Logger.getInstance();

    public ReaderRepository() {
        super(Reader::readerId);
        logger.info("Створено ReaderRepository");
    }

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
