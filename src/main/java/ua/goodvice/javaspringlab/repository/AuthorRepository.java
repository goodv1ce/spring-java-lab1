package ua.goodvice.javaspringlab.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.goodvice.javaspringlab.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author,Integer> {
    @Override
    List<Author> findAll();

    Optional<Author> findByName(String name);

    Optional<Author> findById(int id);

    @Override
    Author save(Author author);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("UPDATE Book book SET book.author = null WHERE book.author.id = :authorId")
    void removeAuthor(@Param("authorId") int authorId);
}
