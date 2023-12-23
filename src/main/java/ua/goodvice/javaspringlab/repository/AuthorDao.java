package ua.goodvice.javaspringlab.repository;

import ua.goodvice.javaspringlab.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findAll();

    Optional<Author> findByName(String name);

    Optional<Author> findById(int id);

    void save(Author author);

    void update(Author author);

    Optional<Author> deleteById(int id);
}
