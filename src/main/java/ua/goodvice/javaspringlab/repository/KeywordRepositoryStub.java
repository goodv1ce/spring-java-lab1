package ua.goodvice.javaspringlab.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.util.FakeDatabaseImplementation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class KeywordRepositoryStub {
    private final FakeDatabaseImplementation database;

    public List<Keyword> findAll() {
        return database.getKeywords();
    }

    public Optional<Keyword> findByValue(String value) {
        return database
                .getKeywords()
                .stream()
                .filter(keyword -> Objects.equals(keyword.getWord(), value))
                .findFirst();
    }

    public Optional<Keyword> findById(Long id) {
        return database
                .getKeywords()
                .stream()
                .filter(keyword -> Objects.equals(keyword.getId(), id))
                .findFirst();
    }

    public void save(Keyword keyword) {
        List<Keyword> keywords = database.getKeywords();
        boolean isKeywordAlreadyInDatabase = keywords.
                stream()
                .anyMatch(keywordFromDatabase -> Objects.equals(keywordFromDatabase.getId(), keyword.getId()));
        if (isKeywordAlreadyInDatabase) {
            Keyword replacedKeyword = keywords.
                    stream()
                    .filter(keywordToBeReplaced -> Objects.equals(keywordToBeReplaced.getId(), keyword.getId()))
                    .findFirst()
                    .orElseThrow();
            int indexOfReplacedBook = keywords.indexOf(replacedKeyword);
            keywords.set(indexOfReplacedBook, keyword);
        } else {
            keywords.add(keyword);
        }
    }

    public void deleteById(Long id) {
        List<Keyword> keywords = database.getKeywords();
        Optional<Keyword> keywordToRemove = keywords.stream()
                .filter(keyword -> Objects.equals(keyword.getId(), id))
                .findFirst();
        keywordToRemove.ifPresent(keywords::remove);
    }
}
