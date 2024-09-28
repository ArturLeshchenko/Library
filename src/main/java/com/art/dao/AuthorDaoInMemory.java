package com.art.dao;

import com.art.entity.Author;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AuthorDaoInMemory implements AuthorDao {
    private static Long id = 0L;
    private final Map<Long, Author> authorMap = new HashMap<>();

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(authorMap.values());
    }

    @Override
    public Optional <Author> findById(Long id) {
        return Optional.ofNullable(authorMap.get(id));
    }

    @Override
    public Author save(Author author) {
        author.setId(++id);
        author = authorMap.put(author.getId(), author);
        return author;
    }

    @Override
    public void delete(Long id) {
        authorMap.remove(id);
    }

    @Override
    public Author update(Long id, Author author) {
        author.setId(id);
        authorMap.put(id, author);
        return author;
    }

}
