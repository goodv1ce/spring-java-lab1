package ua.goodvice.javaspringlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.repository.BookRepositoryStub;

import java.util.List;
import java.util.Set;

@Service
public class BookService {
    private final BookRepositoryStub bookRepository;

    @Autowired
    public BookService(BookRepositoryStub bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findBooksByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow();
    }

    public List<Book> findBooksByKeywords(Set<Keyword> keywords) {
        return bookRepository.findByKeywords(keywords);
    }

    public void addOrUpdateBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

}
