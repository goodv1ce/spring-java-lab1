package ua.goodvice.javaspringlab.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.mapper.KeywordRowMapper;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcKeywordDao implements KeywordDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Keyword> findAll() {
        String sql = "SELECT * FROM keywords";
        return jdbcTemplate.query(sql, new KeywordRowMapper());
    }

    @Override
    public Optional<Keyword> findByValue(String word) {
        try {
            String sql = "SELECT * FROM keywords WHERE word = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new KeywordRowMapper(), word));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Keyword> findById(int id) {
        try {
            String sql = "SELECT * FROM keywords WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new KeywordRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Keyword keyword) {
        String sql = "INSERT INTO keywords (word) VALUES (?)";
        jdbcTemplate.update(sql, keyword.getWord());
    }

    @Override
    public void update(Keyword keyword) {
        String sql = "UPDATE keywords SET word = ? WHERE id = ?";
        jdbcTemplate.update(sql, keyword.getWord(), keyword.getId());
    }

    @Override
    @Transactional
    public Optional<Keyword> deleteById(int id) {
        Optional<Keyword> deletedKeyword = findById(id);
        if (deletedKeyword.isPresent()) {
            String sql = "DELETE FROM book_keywords WHERE keyword_id = ?";
            jdbcTemplate.update(sql, id);
            sql = "DELETE FROM keywords WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }
        return deletedKeyword;
    }
}
