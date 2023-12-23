package ua.goodvice.javaspringlab.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.goodvice.javaspringlab.entity.Keyword;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeywordRowMapper implements RowMapper<Keyword> {
    public Keyword mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Keyword keyword = new Keyword();
        keyword.setId(resultSet.getInt("id"));
        keyword.setWord(resultSet.getString("word"));
        return keyword;
    }
}
