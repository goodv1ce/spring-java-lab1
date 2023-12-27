package ua.goodvice.javaspringlab.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.exception.AuthorNotFoundException;
import ua.goodvice.javaspringlab.service.AuthorService;
import ua.goodvice.javaspringlab.service.BookService;
import ua.goodvice.javaspringlab.service.KeywordService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RESTController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final KeywordService keywordService;

    /**
     * Retrieves a collection of all books
     *
     * @return A collection of all books
     */
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Adds a new book
     *
     * @param book The book to be added
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @PostMapping("/books")
    public ResponseEntity<Object> addNewBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    /**
     * Updates existing book. ID field indicates which book will be updated.
     *
     * @param book The book to be updated.
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @PutMapping("/books")
    public ResponseEntity<Object> updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    /**
     * Retrieves a collection of books filtered by author.
     *
     * @param author Author that book collection will be filtered by.
     * @return Collection of books filtered by author.
     */
    @GetMapping("/books/filter")
    public ResponseEntity<Object> getAllBooksFiltered(@RequestParam String author) {
        return bookService.findBooksByAuthor(authorService.findAuthorByName(author).orElseThrow(()-> new AuthorNotFoundException(author)));
    }

    /**
     * Retrieves a paginated collection of books.
     *
     * @param size quantity of objects to be returned.
     * @return Paginated collection of books.
     */
    @GetMapping("/books/paginate")
    public List<Book> getAllBooksPaginated(@RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooks().stream()
                .limit(size)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves book by ID
     *
     * @param id ID of the book tht will be retrieved.
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable int id) {
        return bookService.findBookById(id);
    }

    /**
     * Deletes book by ID.
     *
     * @param id ID of the book that will be deleted.
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id);
    }

    /**
     * Retrieves a collection of all authors.
     *
     * @return A collection of all authors.
     */
    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    /**
     * Adds a new author
     *
     * @param author The author to be added
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @PostMapping("/authors")
    public ResponseEntity<Object> addNewAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    /**
     * Updates existing author. ID field indicates which author will be updated.
     *
     * @param author The author to be updated.
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @PutMapping("/authors")
    public ResponseEntity<Object> updateAuthor(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }

    /**
     * Deletes author by ID.
     *
     * @param id ID of the author that will be deleted.
     * @return Deleted author.
     */
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable int id) {
        return authorService.deleteAuthor(id);
    }

    /**
     * Retrieves a collection of all keywords.
     *
     * @return A collection of all keywords.
     */
    @GetMapping("/keywords")
    public List<Keyword> getAllKeywords() {
        return keywordService.getAllKeywords();
    }

    /**
     * Adds a new keyword
     *
     * @param keyword The keyword to be added
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @PostMapping("/keywords")
    public ResponseEntity<Object> addNewKeyword(@RequestBody Keyword keyword) {
        return keywordService.saveKeyword(keyword);
    }

    /**
     * Updates existing keyword. ID field indicates which keyword will be updated.
     *
     * @param keyword The keyword to be updated.
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @PutMapping("/keywords")
    public ResponseEntity<Object> updateKeyword(@RequestBody Keyword keyword) {
        return keywordService.updateKeyword(keyword);
    }

    /**
     * Deletes keyword by ID.
     *
     * @param id ID of the keyword that will be deleted.
     * @return ResponseEntity with HttpStatus and an information in the body.
     */
    @DeleteMapping("/keywords/{id}")
    public ResponseEntity<Object> deleteKeyword(@PathVariable int id) {
        return keywordService.deleteKeyword(id);
    }
}
