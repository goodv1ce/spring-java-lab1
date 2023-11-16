package ua.goodvice.javaspringlab.util;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.service.KeywordService;

@Component
@AllArgsConstructor
public class KeywordsFromStringConverter implements Converter<String, Keyword> {
    private final KeywordService keywordService;

    @Override
    public Keyword convert(String source) {
        return keywordService.findKeywordByValue(source);
    }
}
