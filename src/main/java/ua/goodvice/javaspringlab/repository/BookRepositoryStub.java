package ua.goodvice.javaspringlab.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.util.FakeDatabaseImplementation;

import java.util.*;

@Repository
@AllArgsConstructor
public class BookRepositoryStub {
    private final FakeDatabaseImplementation database;

    public List<Book> findAll() {
        return database.getBooks();
    }

    public Optional<Book> findById(Long id) {
        return database
                .getBooks()
                .stream()
                .filter(book -> Objects.equals(book.getId(), id))
                .findFirst();
    }

    public List<Book> findByAuthor(Author author) {
        return database
                .getBooks()
                .stream()
                .filter(book -> Objects.equals(book.getAuthor(), author))
                .toList();
    }

    public Optional<Book> findByTitle(String title) {
        return database
                .getBooks()
                .stream()
                .filter(book -> Objects.equals(book.getTitle(), title))
                .findFirst();
    }

    public List<Book> findByKeywords(Set<Keyword> keywords) {
        return database
                .getBooks()
                .stream()
                .filter(book -> book.getKeywords().containsAll(keywords))
                .toList();
    }

    public void save(Book book) {
        List<Book> books = database.getBooks();
        Long bookId = book.getId();
        boolean isBookAlreadyInDatabase = books.
                stream()
                .anyMatch(bookFromDatabase -> Objects.equals(bookFromDatabase.getId(), book.getId()));
        if (isBookAlreadyInDatabase) {
            Book replacedBook = books.
                    stream()
                    .filter(bookToBeReplaced -> Objects.equals(bookToBeReplaced.getId(), book.getId()))
                    .findFirst()
                    .orElseThrow();
            int indexOfReplacedBook = books.indexOf(replacedBook);
            books.set(indexOfReplacedBook, book);
        } else {
            books.add(book);
        }
    }


    public void deleteById(Long id) {
        List<Book> books = database.getBooks();
        Optional<Book> bookToRemove = books
                .stream()
                .filter(book -> Objects.equals(book.getId(), id))
                .findFirst();
        bookToRemove.ifPresent(books::remove);
    }
}
