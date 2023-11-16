package ua.goodvice.javaspringlab.controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.service.AuthorService;
import ua.goodvice.javaspringlab.service.BookService;
import ua.goodvice.javaspringlab.service.KeywordService;

import java.security.Key;
import java.util.*;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {
    private final AuthorService authorService;
    private final BookService bookService;
    private final KeywordService keywordService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/author")
    public String getAllAuthors(Model model){
        model.addAttribute("authors",authorService.getAllAuthors());
        return "authors";
    }

    @GetMapping("/author/{name}")
    public String getBooksByAuthor(@PathVariable("name") String name, Model model) {
        Author author = authorService.findAuthorByName(name);
        model.addAttribute("name", author.getName());
        List<Book> books = bookService.findBooksByAuthor(author);
        model.addAttribute("books",books);
        return "searchbyauthor";

    }


    @PostMapping("/booktitle")
    public String getBookByTitle(@RequestParam String title, Model model) {
        Book book = bookService.findBookByTitle(title);
        model.addAttribute("book",book);
        return "searchbytitle";
    }

    @GetMapping("/enterbooktitle")
    public String showGetBookByTitleTemplate(){
        return "entertitle";
    }


    @PostMapping("/keyword")
    public String getBookByKeywords(@RequestParam List<String> keywords, Model model) {

        Set<Keyword> keywords1 = new HashSet<>();
        for (int i = 0; i < keywords.size(); i++) {
            keywords1.add(keywordService.findKeywordByValue(keywords.get(i)));
        }

        for (Keyword keyword:
             keywords1) {
            System.out.println("keyword: " + keyword.getWord());

        }
        if (keywords1.size()!=0){
            List<Book> books = bookService.findBooksByKeywords(keywords1);
            model.addAttribute("books",books);
            model.addAttribute("listofkeywords",keywords);
            return "searchbykeywords";
        }
        model.addAttribute("keywords",keywords);
        return "keywords";
    }



    @GetMapping("/keyword")
    public String showKeywordsTemplate() {
        return "keywords";
    }


    @GetMapping("/admin/add")
    public String addNewBook(@ModelAttribute("book")  Book book){
        return "addnewbook";

    }

    @PostMapping("/admin/add")
    public String saveBook(@ModelAttribute("book")  Book book, @RequestParam("keyword") String keyword){
        //
        String[] stringOfKeywords = keyword.split(",");

        Author author = new Author();
        author.setName(book.getAuthor().getName());
        book.setAuthor(author);

        Set<Keyword> keywordSet = new HashSet<>();
        for (String word : stringOfKeywords){
            keywordSet.add(new Keyword(0L,word));
        }
        book.setId((bookService.getAllBooks().get(bookService.getAllBooks().size()-1).getId())+1);
        book.setKeywords(keywordSet);


        bookService.addOrUpdateBook(book);
        return "redirect:/";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/books")
    public String showBooks(Model model){
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books",books);
        return "books";
    }

    @GetMapping("/admin/edit/{id}")
    public String editBookInformation(@PathVariable("id") Long id , Model model){
        if(bookService.findBookById(id).isPresent()){
            Book book = bookService.findBookById(id).get();
            List<String> keywords = new ArrayList<>();
            for (Keyword keyword : book.getKeywords()){
                keywords.add(keyword.getWord());
            }
            model.addAttribute("book",book);
            model.addAttribute("keywords",String.join(",", keywords));
            return "edit";
        }else {

            return "redirect:/admin/books";
        }
    }

    @PostMapping ("/admin/edit/{id}")
    public String saveEditBook(@PathVariable("id") Long id, @RequestParam("title") String title, @RequestParam("author") String authorName, @RequestParam("keywords") String keywords ){
        Set<Keyword> keywordSet = new HashSet<>();
        String[] keywordArray = keywords.split(",");
        for (String word : keywordArray) {
            keywordSet.add(new Keyword(0L, word.trim()));
        }
        Author author = new Author();
        author.setName(authorName);
        Book book = new Book(id, title, author, keywordSet);
        bookService.addOrUpdateBook(book);
        return "redirect:/admin/books";
    }







}
