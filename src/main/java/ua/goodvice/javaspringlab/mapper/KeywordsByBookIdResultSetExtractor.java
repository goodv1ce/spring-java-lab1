package ua.goodvice.javaspringlab.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ua.goodvice.javaspringlab.entity.Keyword;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class KeywordsByBookIdResultSetExtractor implements ResultSetExtractor<Set<Keyword>> {
    @Override
    public Set<Keyword> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Set<Keyword> keywords = new HashSet<>();
        while (resultSet.next()) {
            Keyword keyword = new Keyword();
            keyword.setId(resultSet.getInt("keyword_id"));
            keyword.setWord(resultSet.getString("keyword_word"));
            keywords.add(keyword);
        }
        return keywords;
    }
}
