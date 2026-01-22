package com.library.repository;

import com.library.util.Logger;
import com.library.util.IdentityExtractor;
import java.util.*;

public class GenericRepository<T> {
    private final List<T> items = new ArrayList<>();
    private final IdentityExtractor<T> extractor;
    private final Logger logger = Logger.getInstance();

    public GenericRepository(IdentityExtractor<T> extractor) {
        this.extractor = extractor;
        logger.info("Створено GenericRepository");
    }

    public boolean add(T obj) {
        if (obj == null) {
            logger.warning("Спроба додати null-об'єкт");
            return false;
        }
        Object identity = extractor.getIdentity(obj);
        if (findByIdentity(identity) != null) {
            logger.warning("Дубльований об'єкт з identity: " + identity);
            return false;
        }
        items.add(obj);
        logger.info("Додано об'єкт: " + obj);
        return true;
    }

    public boolean removeByIdentity(Object identity) {
        Iterator<T> iter = items.iterator();
        while (iter.hasNext()) {
            T obj = iter.next();
            if (Objects.equals(extractor.getIdentity(obj), identity)) {
                iter.remove();
                logger.info("Видалено об'єкт з identity: " + identity);
                return true;
            }
        }
        logger.warning("Об'єкт із identity " + identity + " не знайдено для видалення");
        return false;
    }

    public List<T> getAll() {
        logger.info("Отримано всі об'єкти (count=" + items.size() + ")");
        return Collections.unmodifiableList(items);
    }

    public T findByIdentity(Object identity) {
        for (T obj : items) {
            if (Objects.equals(extractor.getIdentity(obj), identity)) {
                logger.info("Знайдено об'єкт по identity: " + identity);
                return obj;
            }
        }
        logger.warning("Об'єкт із identity " + identity + " не знайдено");
        return null;
    }

    public int size() {
        return items.size();
    }

    public void clear() {
        items.clear();
        logger.info("Репозиторій очищено");
    }

    // Новий метод: сортування за identity
    public List<T> sortByIdentity(String order) {
        logger.info("Сортування за identity, порядок: " + order);
        List<T> sorted = new ArrayList<>(items);

        sorted.sort((a, b) -> {
            Object idA = extractor.getIdentity(a);
            Object idB = extractor.getIdentity(b);

            if (idA instanceof Comparable && idB instanceof Comparable) {
                @SuppressWarnings("unchecked")
                Comparable<Object> compA = (Comparable<Object>) idA;
                int cmp = compA.compareTo(idB);
                return "desc".equalsIgnoreCase(order) ? -cmp : cmp;
            }
            return 0;
        });

        logger.info("Сортування завершено, елементів: " + sorted.size());
        return sorted;
    }
}
