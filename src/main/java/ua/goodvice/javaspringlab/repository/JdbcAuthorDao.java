package ua.goodvice.javaspringlab.repository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.mapper.AuthorRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcAuthorDao implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Author> findAll() {
        String sql = "SELECT * FROM authors";
        return jdbcTemplate.query(sql, new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findByName(String name) {
        try {
            String sql = "SELECT * from authors WHERE name = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new AuthorRowMapper(), name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> findById(int id) {
        try {
            String sql = "SELECT * FROM authors WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new AuthorRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO authors (name) VALUES (?)", new String[]{"id"});
            ps.setString(1, author.getName());
            return ps;
        };
        jdbcTemplate.update(psc, keyHolder);
        author.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public void update(Author author) {
        String sql = "UPDATE authors SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, author.getName(), author.getId());
    }

    @Override
    @Transactional
    public Optional<Author> deleteById(int id) {
        Optional<Author> deletedAuthor = findById(id);
        if (deletedAuthor.isPresent()) {
            String sql = "UPDATE books SET author_id = NULL WHERE author_id = ?";
            jdbcTemplate.update(sql, id);
            sql = "DELETE FROM authors WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }
        return deletedAuthor;
    }
}
