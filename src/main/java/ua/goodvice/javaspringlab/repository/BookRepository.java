package ua.goodvice.javaspringlab.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookRepository extends CrudRepository<Book,Integer> {
    @Override
    List<Book> findAll();

    List<Book> findByAuthor(Author author);

    Optional<Book> findByTitle(String title);

    @Query("SELECT book FROM Book book JOIN book.keywords keyword WHERE keyword IN :keywords")
    List<Book> findByKeywords(@Param("keywords") Set<Keyword> keywords);

    Optional<Book> findById(int id);

    @Override
    Book save(Book book);

    void deleteById(int id);
}
