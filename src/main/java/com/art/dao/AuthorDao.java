package com.art.dao;

import com.art.entity.Author;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    Author save(Author author);

    void delete(Long id);

    Author update(Long id, Author author);
}
