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
    public String index(){
        return "index";
    }

    @GetMapping("/author")
    public String getAllAuthors(Model model){
        model.addAttribute("authors",authorService.getAllAuthors());
        return "authors";
    }

    @GetMapping("/author/{name}")
    public String getAuthor(@PathVariable("name") String name, Model model) {
        Author author = authorService.findAuthorByName(name);
        model.addAttribute("name", author.getName());
        List<Book> books = bookService.findBooksByAuthor(author);
        model.addAttribute("books",books);
        return "searchbyauthor";

    }


    @PostMapping("/booktitle")
    public String searchBookByName(@RequestParam String title, Model model) {
        Book book = bookService.findBookByTitle(title);
        model.addAttribute("book",book);
        return "searchbytitle";
    }

    @GetMapping("/enterbooktitle")
    public String showSearchBookByTitleform(){
        return "entertitle";
    }


    @PostMapping("/keyword")
    public String searchBookByKeywords(@RequestParam List<String> keywords, Model model) {

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
    public String showform() {
        return "keywords";
    }



}
