package com.art.dao;

import com.art.entity.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> findAll();


    Author findById(Long id);

    Author save(Author author);

    void delete(Long id);

    void update(Long id, Author author);
}
