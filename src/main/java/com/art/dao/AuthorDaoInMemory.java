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
    public Author findById(Long id) {
        return authorMap.get(id);
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
    public void update(Long id, Author author) {
        author.setId(id);
        authorMap.put(id, author);
    }

}
