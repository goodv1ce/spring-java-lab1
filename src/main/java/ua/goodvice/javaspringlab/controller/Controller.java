package ua.goodvice.javaspringlab.controller;

import lombok.AllArgsConstructor;
import ua.goodvice.javaspringlab.service.AuthorService;
import ua.goodvice.javaspringlab.service.BookService;
import ua.goodvice.javaspringlab.service.KeywordService;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {
    private final AuthorService authorService;
    private final BookService bookService;
    private final KeywordService keywordService;

}
