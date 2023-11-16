package ua.goodvice.javaspringlab.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.repository.AuthorRepositoryStub;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepositoryStub authorRepository;

    public Author findAuthorByName(String name) {
        return authorRepository.findByName(name).orElseThrow();
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

}
