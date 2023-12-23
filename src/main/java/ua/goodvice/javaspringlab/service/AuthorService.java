package ua.goodvice.javaspringlab.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.repository.JdbcAuthorDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private JdbcAuthorDao authorRepository;

    public Optional<Author> findAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    public Optional<Author> findAuthorById(int id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public ResponseEntity<Object> saveAuthor(Author author) {
        StringBuilder errorBuilder = new StringBuilder();
        if (author.getName() == null) {
            errorBuilder.append("Author must have 'name' key");
        } else if (author.getName().isEmpty()) {
            errorBuilder.append("Author name cannot be empty.");
        }
        if (errorBuilder.isEmpty()) {
            authorRepository.save(author);
            return ResponseEntity.status(HttpStatus.OK).body(author);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBuilder.toString());
        }
    }

    public ResponseEntity<Object> updateAuthor(Author author) {
        StringBuilder errorBuilder = new StringBuilder();
        int authorId = author.getId();
        if (authorId == 0) {
            errorBuilder.append("ID of the author to be updated is not specified.\n");
        } else {
            if (findAuthorById(authorId).isEmpty()) {
                errorBuilder.append("Author with ID \n").append(authorId).append(" does not exist.");
            }
        }
        if (errorBuilder.isEmpty()) {
            authorRepository.update(author);
            return ResponseEntity.status(HttpStatus.OK).body(author);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBuilder.toString());
        }

    }

    public ResponseEntity<Object> deleteAuthor(int id) {
        StringBuilder errorWriter = new StringBuilder();
        Optional<Author> deletedAuthor = authorRepository.deleteById(id);
        if (deletedAuthor.isEmpty()) {
            errorWriter.append("Author with ID ").append(id).append(" does not exist.\n");
        }
        if (errorWriter.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Author has been deleted successfully");
            response.put("deleted_author", deletedAuthor.orElse(null));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorWriter);
        }
    }

}
