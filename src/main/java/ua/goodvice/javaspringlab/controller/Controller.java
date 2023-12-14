package ua.goodvice.javaspringlab.controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.service.AuthorService;
import ua.goodvice.javaspringlab.service.BookService;
import ua.goodvice.javaspringlab.service.KeywordService;

import java.util.*;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {
    private final AuthorService authorService;
    private final BookService bookService;
    private final KeywordService keywordService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/search/authors")
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "searchByAuthor";
    }

    @GetMapping("/search/authors/{name}")
    public String getBooksByAuthor(@PathVariable("name") String name, Model model) {
        Author author = authorService.findAuthorByName(name);
        List<Book> books = bookService.findBooksByAuthor(author);
        model.addAttribute("name", author.getName());
        model.addAttribute("books", books);
        return "searchByAuthorResult";
    }

    @GetMapping("/search/title")
    public String showGetBookByTitleTemplate() {
        return "searchByTitle";
    }

    @PostMapping("/search/title")
    public String getBookByTitle(@RequestParam String title, Model model) {
        Book book = bookService.findBookByTitle(title);
        model.addAttribute("book", book);
        return "searchByTitleResult";
    }

    @GetMapping("/search/keywords")
    public String showKeywordsTemplate(Model model) {
        List<Keyword> allKeywords = keywordService.getAllKeywords();
        List<String> stringOfAllKeywords = new ArrayList<>();
        for (Keyword keyword : allKeywords) {
            stringOfAllKeywords.add(keyword.getWord());
        }
        model.addAttribute("allkeywords", stringOfAllKeywords);
        return "searchByKeywords";
    }

    @PostMapping("/search/keywords")
    public String getBookByKeywords(@RequestParam Set<Keyword> keywords, Model model) {
        List<Book> books = bookService.findBooksByKeywords(keywords);
        model.addAttribute("books", books);
        model.addAttribute("keywords", keywords);
        return "searchByKeywordsResult";
    }


    @GetMapping("/admin/books/add")
    public String showAddBookForm(@ModelAttribute("book") Book book) {
        return "addBookForm";

    }

    // book CRUD
    @GetMapping("/admin/books")
    public String showAdminBooksCatalogue(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "adminBooksCatalogue";
    }

    @PostMapping("/admin/books")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping("/admin/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/books/edit/{id}")
    public String editBookInformation(@PathVariable("id") Long id, Model model) {
        if (bookService.findBookById(id).isPresent()) {
            Book book = bookService.findBookById(id).get();
            List<String> keywords = new ArrayList<>();
            for (Keyword keyword : book.getKeywords()) {
                keywords.add(keyword.getWord());
            }
            model.addAttribute("book", book);
            model.addAttribute("keywords", String.join(",", keywords));
            return "editBookForm";
        } else {

            return "redirect:/admin/books";
        }
    }

    @PostMapping("/admin/edit/{id}")
    public String saveEditBook(@PathVariable("id") Long id, @RequestParam("title") String title, @RequestParam("author") String authorName, @RequestParam("keywords") String keywords) {
        Set<Keyword> keywordSet = new HashSet<>();
        String[] keywordArray = keywords.split(",");
        for (String word : keywordArray) {
            keywordSet.add(new Keyword(0L, word.trim()));
        }
        Author author = new Author();
        author.setName(authorName);
        Book book = new Book(id, title, author, keywordSet);
        bookService.saveBook(book);
        return "redirect:/admin/books";
    }
}
