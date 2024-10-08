package com.art.service;

import com.art.entity.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findById(Long id);

    Author save(Author author);

    void delete(Long id);

    Author update(Long id, Author author);

}

