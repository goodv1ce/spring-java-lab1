package ua.goodvice.javaspringlab.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.goodvice.javaspringlab.entity.Keyword;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends CrudRepository<Keyword,Integer> {
    @Override
    List<Keyword> findAll();

    Optional<Keyword> findByWord(String word);

    Optional<Keyword> findById(int id);
    @Override
    Keyword save(Keyword keyword);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM book_keywords WHERE keyword_id = :keywordId",nativeQuery = true)
    void deleteKeywordsById(@Param("keywordId") int keywordId);

}
