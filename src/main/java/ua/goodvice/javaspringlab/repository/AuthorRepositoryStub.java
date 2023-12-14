package ua.goodvice.javaspringlab.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.util.FakeDatabaseImplementation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthorRepositoryStub {
    private final FakeDatabaseImplementation database;

    public List<Author> findAll() {
        return database.getAuthors();
    }

    public Optional<Author> findByName(String name) {
        return database
                .getAuthors()
                .stream()
                .filter(author -> Objects.equals(author.getName(), name))
                .findFirst();
    }

    public Optional<Author> findById(Long id) {
        return database
                .getAuthors()
                .stream()
                .filter(author -> Objects.equals(author.getId(), id))
                .findFirst();
    }

    public void save(Author author) {
        List<Author> authors = database.getAuthors();
        boolean isAuthorAlreadyInDatabase = authors.
                stream()
                .anyMatch(bookFromDatabase -> Objects.equals(bookFromDatabase.getId(), author.getId()));
        if (isAuthorAlreadyInDatabase) {
            Author replacedBook = authors.
                    stream()
                    .filter(bookToBeReplaced -> Objects.equals(bookToBeReplaced.getId(), author.getId()))
                    .findFirst()
                    .orElseThrow();
            int indexOfReplacedAuthor = authors.indexOf(replacedBook);
            authors.set(indexOfReplacedAuthor, author);
        } else {
            authors.add(author);
        }
    }

    public void deleteById(Long id) {
        List<Author> authors = database.getAuthors();
        Optional<Author> authorToRemove = authors
                .stream()
                .filter(author -> Objects.equals(author.getId(), id))
                .findFirst();
        authorToRemove.ifPresent(authors::remove);
    }
}
