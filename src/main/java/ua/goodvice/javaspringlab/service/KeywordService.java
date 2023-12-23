package ua.goodvice.javaspringlab.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.repository.JdbcKeywordDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KeywordService {
    private final JdbcKeywordDao keywordRepository;

    public Optional<Keyword> findKeywordByValue(String value) {
        return keywordRepository.findByValue(value);
    }

    public Optional<Keyword> findKeywordById(int id) {
        return keywordRepository.findById(id);
    }

    public List<Keyword> getAllKeywords() {
        return keywordRepository.findAll();
    }

    public ResponseEntity<Object> saveKeyword(Keyword keyword) {
        StringBuilder errorBuilder = new StringBuilder();
        if (keyword.getWord() == null) {
            errorBuilder.append("Keyword must have 'word' key");
        } else if (keyword.getWord().isEmpty()) {
            errorBuilder.append("Keyword word cannot be empty.");
        }
        if (errorBuilder.isEmpty()) {
            keywordRepository.save(keyword);
            return ResponseEntity.status(HttpStatus.OK).body(keyword);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBuilder.toString());
        }
    }

    public ResponseEntity<Object> updateKeyword(Keyword keyword) {
        StringBuilder errorBuilder = new StringBuilder();
        int keywordId = keyword.getId();
        if (keywordId == 0) {
            errorBuilder.append("ID of the keyword to be updated is not specified.\n");
        } else {
            if (findKeywordById(keywordId).isEmpty()) {
                errorBuilder.append("Keyword with ID \n").append(keywordId).append(" does not exist.");
            }
        }
        if (errorBuilder.isEmpty()) {
            keywordRepository.update(keyword);
            return ResponseEntity.status(HttpStatus.OK).body(keyword);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBuilder.toString());
        }
    }

    public ResponseEntity<Object> deleteKeyword(int id) {
        StringBuilder errorWriter = new StringBuilder();
        Optional<Keyword> deletedKeyword = keywordRepository.deleteById(id);
        if (deletedKeyword.isEmpty()) {
            errorWriter.append("Keyword with ID ").append(id).append(" does not exist.\n");
        }
        if (errorWriter.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Keyword has been deleted successfully");
            response.put("deleted_keyword", deletedKeyword.orElse(null));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorWriter);
        }
    }

}
