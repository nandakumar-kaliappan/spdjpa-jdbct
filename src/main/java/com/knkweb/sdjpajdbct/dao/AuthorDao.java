package com.knkweb.sdjpajdbct.dao;

import com.knkweb.sdjpajdbct.domain.Author;

public interface AuthorDao {
    Author getByName(String firstName, String lastName);
    Author getById(Long iduthor );

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long authorId);
}
