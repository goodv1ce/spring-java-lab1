package ua.goodvice.javaspringlab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.repository.KeywordRepositoryStub;

import java.util.List;

@Service
@AllArgsConstructor
public class KeywordService {
    private final KeywordRepositoryStub keywordRepository;

    public Keyword findKeywordByValue(String value) {
        return keywordRepository.findByValue(value).orElseThrow();
    }

    public List<Keyword> getAllKeywords(){
        return keywordRepository.findAll();
    }
}
