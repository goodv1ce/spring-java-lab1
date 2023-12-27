package ua.goodvice.javaspringlab.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.repository.BookRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final KeywordService keywordService;

    public ResponseEntity<Object> findBooksByAuthor(Author author) {
        StringBuilder errorBuilder = new StringBuilder();
        if (author.getName() == null) {
            errorBuilder.append("Invalid author name.\n");
        }
        Optional<Author> authorFromDatasource = authorService.findAuthorByName(author.getName());
        if (authorFromDatasource.isEmpty()) {
            errorBuilder.append("Author with name ").append(author.getName()).append(" does not exist.\n");
        }
        if (errorBuilder.isEmpty()) {
            List<Book> books = bookRepository.findByAuthor(authorFromDatasource.orElseThrow());
            return ResponseEntity.status(HttpStatus.OK).body(books);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBuilder.toString());
        }
    }

    public Optional<Book> findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findBooksByKeywords(Set<Keyword> keywords) {
        return bookRepository.findByKeywords(keywords);
    }

    /**
     * Save the book in the datasource. Validates author and keywords, checks if existed.
     * The error log is filled during the validation. If validation failed, 404 is returned and a log in the body.
     * If validation passed, 200 is returned and an entity in the body.
     *
     * @param book object to be added in datasource. Author id and keyword id fields are ignoring.
     * @return ResponseEntity object with result of the saving.
     */

    public ResponseEntity<Object> saveBook(Book book) {
        StringBuilder responseErrorsWriter = new StringBuilder();
        Optional<Author> datasourceAuthor = authorService.findAuthorByName(book.getAuthor().getName());
        if (datasourceAuthor.isEmpty()) {
            responseErrorsWriter.append("Author ").append(book.getAuthor().getName()).append(" is not exist.\n");
        }
        Set<Keyword> requestBookKeywords = book.getKeywords();
        for (Keyword keyword : requestBookKeywords) {
            Optional<Keyword> datasourceKeyword = keywordService.findKeywordByValue(keyword.getWord());
            if (datasourceKeyword.isEmpty()) {
                responseErrorsWriter.append("Keyword ").append(keyword.getWord()).append(" is not exist.\n");
            }
        }
        if (responseErrorsWriter.isEmpty()) {
            Author author = book.getAuthor();
            int authorId = authorService.findAuthorByName(author.getName()).orElseThrow().getId();
            author.setId(authorId);
            for (Keyword keyword : book.getKeywords()) {
                int keywordId = keywordService.findKeywordByValue(keyword.getWord()).orElseThrow().getId();
                keyword.setId(keywordId);
            }
            bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorsWriter.toString());
        }
    }

    public ResponseEntity<Object> updateBook(Book book) {
        StringBuilder responseErrorsWriter = new StringBuilder();
        if (book.getId() == 0) {
            responseErrorsWriter.append("ID of the book to be updated is not specified.\n");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorsWriter.toString());
        }
        try {
            Book bookFromDatasource = bookRepository.findById(book.getId()).orElseThrow();
            if (book.getTitle() == null) {
                book.setTitle(bookFromDatasource.getTitle());
            }
            if (book.getAuthor() == null) {
                book.setAuthor(bookFromDatasource.getAuthor());
            } else {
                Author author = book.getAuthor();
                int authorId = author.getId();
                String authorName = author.getName();
                if (authorId == 0 && authorName != null) {
                    try {
                        author.setId(authorService.findAuthorByName(authorName).orElseThrow().getId());
                    } catch (NoSuchElementException e) {
                        responseErrorsWriter.append("Author with such name does not exist.\n");
                    }
                } else if (authorId == 0) {
                    responseErrorsWriter.append("Bad author data.\n");
                }
            }
            if (book.getKeywords() == null) {
                book.setKeywords(bookFromDatasource.getKeywords());
            } else {
                Set<Keyword> keywords = book.getKeywords();
                for (Keyword keyword : keywords) {
                    if (keyword.getId() == 0 && keyword.getWord() != null) {
                        try {
                            keyword.setId(keywordService.findKeywordByValue(keyword.getWord()).orElseThrow().getId());
                        } catch (NoSuchElementException e) {
                            responseErrorsWriter.append("Such keyword does not exist.\n");
                        }
                    } else if (keyword.getId() == 0) {
                        responseErrorsWriter.append("Bad keyword data\n");
                    }
                }
            }
        } catch (NoSuchElementException e) {
            responseErrorsWriter.append("Book with such ID does not exist");
        }
        if (responseErrorsWriter.isEmpty()) {
            bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorsWriter.toString());
        }

    }

    public ResponseEntity<Object> deleteBook(int id) {
        StringBuilder errorWriter = new StringBuilder();
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.deleteById(id);
        if (book.isEmpty()) {
            errorWriter.append("Book with id ").append(id).append(" does not exist.\n");
        }
        if (errorWriter.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book has been deleted successfully");
            //response.put("deleted_book", book.orElse(null));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorWriter);
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public ResponseEntity<Object> findBookById(int id) {
        StringBuilder errorWriter = new StringBuilder();
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            errorWriter.append("Book with id ").append(id).append(" does not exist.\n");
        }
        if (errorWriter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorWriter);
        }
    }
}
