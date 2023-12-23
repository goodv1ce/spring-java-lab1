package ua.goodvice.javaspringlab.repository;

import ua.goodvice.javaspringlab.entity.Keyword;

import java.util.List;
import java.util.Optional;

public interface KeywordDao {
    List<Keyword> findAll();

    Optional<Keyword> findByValue(String word);

    Optional<Keyword> findById(int id);

    void save(Keyword keyword);

    void update(Keyword keyword);

    Optional<Keyword> deleteById(int id);
}
