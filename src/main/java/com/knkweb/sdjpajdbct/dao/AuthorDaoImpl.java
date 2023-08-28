package com.knkweb.sdjpajdbct.dao;

import com.knkweb.sdjpajdbct.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class AuthorDaoImpl implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getByName(String firstName, String lastName) {

        return jdbcTemplate.queryForObject("select * from author where first_name = ? and " +
                "last_name = ?", getAuthorMapper(), firstName, lastName);
    }

    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", getAuthorMapper(),
                id);
    }

    private RowMapper<Author> getAuthorMapper() {
        return new AuthorMapper();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update("Insert into  author (first_name,last_name) values (?,?)",
                author.getFirstName(),
                author.getLastName());
        Long insertedId = jdbcTemplate.queryForObject("Select LAST_INSERT_ID()", Long.class);
        return this.getById(insertedId);
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update("UPDATE author set first_name = ?, last_name = ?",
                author.getFirstName(), author.getLastName());

        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long authorId) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ? ",authorId);
    }
}
