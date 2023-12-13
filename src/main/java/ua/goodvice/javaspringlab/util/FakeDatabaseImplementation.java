package ua.goodvice.javaspringlab.util;

import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FakeDatabaseImplementation {
    private final List<Author> authorTableImplementation = new ArrayList<>();
    private final List<Book> bookTableImplementation = new ArrayList<>();
    private final List<Keyword> keywordTableImplementation = new ArrayList<>();

    public FakeDatabaseImplementation() {
        // keywords initializing
        Keyword keyword1 = new Keyword(0L, "Fantasy");
        Keyword keyword2 = new Keyword(0L, "Thriller");
        Keyword keyword3 = new Keyword(0L, "Horror");
        Keyword keyword4 = new Keyword(0L, "Comedy");

        keywordTableImplementation.add(keyword1);
        keywordTableImplementation.add(keyword2);
        keywordTableImplementation.add(keyword3);
        keywordTableImplementation.add(keyword4);

        // authors initializing
        Author author1 = new Author(0L, "TestAuthor1");
        Author author2 = new Author(1L, "TestAuthor2");
        Author author3 = new Author(2L,"TestAuthor3");

        authorTableImplementation.add(author1);
        authorTableImplementation.add(author2);
        authorTableImplementation.add(author3);


        // books initializing
        Book book1 = new Book(0L, "TestBook1", author1, Set.of(keyword1, keyword3));
        Book book2 = new Book(1L, "TestBook2", author2, Set.of(keyword1, keyword2, keyword4));
        Book book3 = new Book(2L, "TestBook3", author1, Set.of(keyword3, keyword4));

        bookTableImplementation.add(book1);
        bookTableImplementation.add(book2);
        bookTableImplementation.add(book3);
    }

    public List<Author> getAuthors() {
        return authorTableImplementation;
    }

    public List<Book> getBooks() {
        return bookTableImplementation;
    }

    public List<Keyword> getKeywords() {
        return keywordTableImplementation;
    }
}
