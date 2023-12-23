package ua.goodvice.javaspringlab.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SingleBookResultSetExtractor implements ResultSetExtractor<Optional<Book>> {
    @Override
    public Optional<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (resultSet.next()) {
            Book book = new Book();
            Author author = new Author();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));

            author.setId(resultSet.getInt("author_id"));
            author.setName(resultSet.getString("author_name"));
            book.setAuthor(author);

            Set<Keyword> keywords = new HashSet<>();
            do {
                Keyword keyword = new Keyword();
                keyword.setId(resultSet.getInt("keyword_id"));
                keyword.setWord(resultSet.getString("keyword_word"));
                keywords.add(keyword);
            } while (resultSet.next());
            book.setKeywords(keywords);
            return Optional.of(book);
        } else {
            return Optional.empty();
        }

    }
}
