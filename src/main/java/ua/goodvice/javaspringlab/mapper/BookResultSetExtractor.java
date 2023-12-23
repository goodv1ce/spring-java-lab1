package ua.goodvice.javaspringlab.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ua.goodvice.javaspringlab.entity.Author;
import ua.goodvice.javaspringlab.entity.Book;
import ua.goodvice.javaspringlab.entity.Keyword;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {

    @Override
    public List<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Book> bookMap = new HashMap<>();
        while (resultSet.next()) {
            int currentBookId = resultSet.getInt("id");
            if (!bookMap.containsKey(currentBookId)) {
                Book book = new Book();
                book.setId(currentBookId);
                book.setTitle(resultSet.getString("title"));

                Author author = new Author();
                author.setId(resultSet.getInt("author_id"));
                author.setName(resultSet.getString("author_name"));
                book.setAuthor(author);

                Set<Keyword> keywords = new HashSet<>();
                Keyword keyword = new Keyword();
                keyword.setId(resultSet.getInt("keyword_id"));
                keyword.setWord(resultSet.getString("keyword_word"));
                keywords.add(keyword);
                book.setKeywords(keywords);
                bookMap.put(book.getId(), book);
            } else {
                Book book = bookMap.get(currentBookId);
                Set<Keyword> keywords = book.getKeywords();
                Keyword keyword = new Keyword();
                keyword.setId(resultSet.getInt("keyword_id"));
                keyword.setWord(resultSet.getString("keyword_word"));
                keywords.add(keyword);
            }
        }
        return new ArrayList<>(bookMap.values());
    }
}
