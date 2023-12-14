package ua.goodvice.javaspringlab.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.service.AuthorService;
import ua.goodvice.javaspringlab.service.BookService;
import ua.goodvice.javaspringlab.service.KeywordService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RESTController {
    private final AuthorService authorService;
    private final BookService bookService;
    private final KeywordService keywordService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/filter")
    public List<Book> getAllBooksFiltered(@RequestParam String author) {
        return bookService.findBooksByAuthor(authorService.findAuthorByName(author));
    }

    @GetMapping("/books/paginate")
    public List<Book> getAllBooksPaginated(@RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooks().stream()
                .limit(size)
                .collect(Collectors.toList());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.findBookById(id);
        if (book.isPresent()) {
            ResponseEntity.ok(book);
            return ResponseEntity.ok(book.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/books")
    public Book addNewBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return book;
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return book;
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        Optional<Book> bookToBeDeleted = bookService.findBookById(id);
        if (bookToBeDeleted.isPresent()) {
            bookService.deleteBook(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book has been deleted successfully");
            response.put("deletedBook", bookToBeDeleted.get());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping("/authors")
    public Author addNewAuthor(@RequestBody Author author) {
        authorService.saveAuthor(author);
        return author;
    }


    @PutMapping("/authors")
    public Author updateAuthor(@RequestBody Author author) {
        authorService.saveAuthor(author);
        return author;
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        Optional<Author> authorToBeDeleted = authorService.findAuthorById(id);
        if (authorToBeDeleted.isPresent()) {
            authorService.deleteAuthor(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Author has been deleted successfully");
            response.put("deletedAuthor", authorToBeDeleted.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/keywords")
    public List<Keyword> getAllKeywords() {
        return keywordService.getAllKeywords();
    }

    @PostMapping("/keywords")
    public Keyword addNewKeyword(@RequestBody Keyword keyword) {
        keywordService.saveKeyword(keyword);
        return keyword;
    }


    @PutMapping("/keywords")
    public Keyword updateKeyword(@RequestBody Keyword keyword) {
        keywordService.saveKeyword(keyword);
        return keyword;
    }

    @DeleteMapping("/keywords/{id}")
    public ResponseEntity<Object> deleteKeyword(@PathVariable Long id) {
        Optional<Keyword> keywordToBeDeleted = keywordService.findKeywordById(id);
        if (keywordToBeDeleted.isPresent()) {
            keywordService.deleteKeyword(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Keyword has been deleted successfully");
            response.put("deletedKeyword", keywordToBeDeleted.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}
