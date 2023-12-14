package ua.goodvice.javaspringlab.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.repository.AuthorRepositoryStub;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorRepositoryStub authorRepository;

    public Author findAuthorByName(String name) {
        return authorRepository.findByName(name).orElseThrow();
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

}
