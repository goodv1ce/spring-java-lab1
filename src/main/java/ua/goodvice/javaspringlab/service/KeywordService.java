package ua.goodvice.javaspringlab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.repository.KeywordRepositoryStub;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KeywordService {
    private final KeywordRepositoryStub keywordRepository;

    public Keyword findKeywordByValue(String value) {
        return keywordRepository.findByValue(value).orElseThrow();
    }

    public Optional<Keyword> findKeywordById(Long id) {
        return keywordRepository.findById(id);
    }

    public List<Keyword> getAllKeywords() {
        return keywordRepository.findAll();
    }

    public void saveKeyword(Keyword keyword) {
        keywordRepository.save(keyword);
    }

    public void deleteKeyword(Long id) {
        keywordRepository.deleteById(id);
    }

}
