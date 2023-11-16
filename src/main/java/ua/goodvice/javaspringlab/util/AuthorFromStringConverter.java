package ua.goodvice.javaspringlab.util;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.service.AuthorService;

@Component
@AllArgsConstructor
public class AuthorFromStringConverter implements Converter<String, Author> {
    private final AuthorService authorService;

    @Override
    public Author convert(String source) {
        return authorService.findAuthorByName(source);
    }
}
