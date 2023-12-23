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
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;
import ua.goodvice.javaspringlab.mapper.SingleBookResultSetExtractor;
import ua.goodvice.javaspringlab.mapper.BookResultSetExtractor;
import ua.goodvice.javaspringlab.mapper.KeywordsByBookIdResultSetExtractor;

import java.sql.PreparedStatement;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class JdbcBookDao implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAll() {
        String sql = "SELECT books.id, books.title, " +
                "authors.id AS author_id, " +
                "authors.name AS author_name, " +
                "keywords.id AS keyword_id, " +
                "keywords.word AS keyword_word " +
                "FROM books " +
                "INNER JOIN authors ON books.author_id = authors.id " +
                "LEFT JOIN book_keywords ON books.id = book_keywords.book_id " +
                "LEFT JOIN keywords ON book_keywords.keyword_id = keywords.id ";
        return jdbcTemplate.query(sql, new BookResultSetExtractor());
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        String sql = "SELECT books.id, books.title, " +
                "authors.id AS author_id, " +
                "authors.name AS author_name, " +
                "keywords.id AS keyword_id, " +
                "keywords.word AS keyword_word " +
                "FROM books " +
                "INNER JOIN authors ON books.author_id = authors.id " +
                "LEFT JOIN book_keywords ON books.id = book_keywords.book_id " +
                "LEFT JOIN keywords ON book_keywords.keyword_id = keywords.id " +
                "WHERE books.author_id = ?";
        return jdbcTemplate.query(sql, new BookResultSetExtractor(), author.getId());
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        try {
            String sql = "SELECT books.id, books.title, " +
                    "authors.id AS author_id, " +
                    "authors.name AS author_name, " +
                    "keywords.id AS keyword_id, " +
                    "keywords.word AS keyword_word " +
                    "FROM books " +
                    "INNER JOIN authors ON books.author_id = authors.id " +
                    "LEFT JOIN book_keywords ON books.id = book_keywords.book_id " +
                    "LEFT JOIN keywords ON book_keywords.keyword_id = keywords.id " +
                    "WHERE books.title = ?";
            return jdbcTemplate.query(sql, new SingleBookResultSetExtractor(), title);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findByKeywords(Set<Keyword> keywords) {
        String sql = "SELECT books.id, books.title, " +
                "authors.id AS author_id, " +
                "authors.name AS author_name, " +
                "keywords.id AS keyword_id, " +
                "keywords.word AS keyword_word " +
                "FROM books " +
                "INNER JOIN authors ON books.author_id = authors.id " +
                "LEFT JOIN book_keywords ON books.id = book_keywords.book_id " +
                "LEFT JOIN keywords ON book_keywords.keyword_id = keywords.id " +
                "WHERE keywords.word = ?";
        if (keywords.isEmpty()) {
            return new ArrayList<>();
        } else if (keywords.size() == 1) {
            return jdbcTemplate.query(sql, new BookResultSetExtractor(), keywords.iterator().next().getWord());
        } else {
            Iterator<Keyword> keywordIterator = keywords.iterator();
            List<Book> books = jdbcTemplate.query(sql, new BookResultSetExtractor(), keywordIterator.next().getWord());
            List<Book> resultBookList = new ArrayList<>();
            if (books != null) {
                for (Book book : books) {
                    if (book.getKeywords().containsAll(keywords)) {
                        resultBookList.add(book);
                    }
                }
            }
            return resultBookList;
        }
    }


    @Override
    public Optional<Book> findById(int id) {
        try {
            String sql = "SELECT books.id, books.title, " +
                    "authors.id AS author_id, " +
                    "authors.name AS author_name, " +
                    "keywords.id AS keyword_id, " +
                    "keywords.word AS keyword_word " +
                    "FROM books " +
                    "INNER JOIN authors ON books.author_id = authors.id " +
                    "LEFT JOIN book_keywords ON books.id = book_keywords.book_id " +
                    "LEFT JOIN keywords ON book_keywords.keyword_id = keywords.id " +
                    "WHERE books.id = ?";
            return jdbcTemplate.query(sql, new SingleBookResultSetExtractor(), id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Adds the book to the datasource.
     *
     * @param book {@link Book} object to be saved
     */
    @Override
    @Transactional
    public void save(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO books (title, author_id) VALUES (?, ?)", new String[]{"id"});
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());
            return ps;
        };
        jdbcTemplate.update(psc, keyHolder);
        book.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        String sql = "INSERT INTO book_keywords (book_id, keyword_id) VALUES (?, ?)";
        Set<Keyword> keywords = book.getKeywords();
        for (Keyword keyword : keywords) {
            jdbcTemplate.update(sql, book.getId(), keyword.getId());
        }
    }

    /**
     * Updates the existing book in the datasource
     *
     * @param book {@link Book} object to be updated
     */
    @Override
    @Transactional
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor().getId(), book.getId());

        if (book.getKeywords() != null) {
            sql = "SELECT keyword_id, " +
                    "keywords.word as keyword_word " +
                    "FROM book_keywords " +
                    "LEFT JOIN keywords ON book_keywords.keyword_id = keywords.id  " +
                    "WHERE book_id = ?";
            Set<Keyword> keywordsInDatasource = jdbcTemplate.query(
                    sql, new KeywordsByBookIdResultSetExtractor(), book.getId());
            if (keywordsInDatasource != null) {
                Set<Keyword> keywordsToBeDeleted = new HashSet<>(keywordsInDatasource);
                keywordsToBeDeleted.removeIf(keyword -> book.getKeywords().stream()
                        .anyMatch(k -> k.getId() == keyword.getId()));
                sql = "DELETE FROM book_keywords WHERE book_id = ? AND keyword_id = ?";
                for (Keyword keywordToBeDeleted : keywordsToBeDeleted) {
                    jdbcTemplate.update(sql, book.getId(), keywordToBeDeleted.getId());
                }
            }
            Set<Keyword> keywordsToBeAdded = new HashSet<>(book.getKeywords());
            if (keywordsInDatasource != null) {
                keywordsToBeAdded.removeAll(keywordsInDatasource);
            }
            sql = "INSERT INTO book_keywords (book_id, keyword_id) VALUES (?, ?)";
            for (Keyword keywordToBeAdded : keywordsToBeAdded) {
                jdbcTemplate.update(sql, book.getId(), keywordToBeAdded.getId());
            }
        }
    }

    @Override
    @Transactional
    public Optional<Book> deleteById(int id) {
        Optional<Book> bookToBeDeleted = findById(id);
        if (bookToBeDeleted.isPresent()) {
            String sql = "DELETE FROM book_keywords WHERE book_id = ?";
            jdbcTemplate.update(sql, id);
            sql = "DELETE FROM books WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }
        return bookToBeDeleted;
    }

}
