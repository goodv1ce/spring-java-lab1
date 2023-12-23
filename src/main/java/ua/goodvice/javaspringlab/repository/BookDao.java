package ua.goodvice.javaspringlab.repository;

import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookDao {
    List<Book> findAll();

    List<Book> findByAuthor(Author author);

    Optional<Book> findByTitle(String title);

    List<Book> findByKeywords(Set<Keyword> keywords);

    Optional<Book> findById(int id);

    void save(Book book);

    void update(Book book);

    Optional<Book> deleteById(int id);
}
