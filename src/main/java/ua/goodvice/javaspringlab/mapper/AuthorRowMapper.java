package ua.goodvice.javaspringlab.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.goodvice.javaspringlab.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthorRowMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getInt("id"));
        author.setName(resultSet.getString("name"));
        return author;
    }
}
