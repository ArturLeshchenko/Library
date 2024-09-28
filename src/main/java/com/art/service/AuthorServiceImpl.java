package com.art.service;

import com.art.dao.AuthorDao;
import com.art.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{

private final AuthorDao authorDao;
    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorDao.findById(id);
    }

    @Override
    public Author save(Author author) {
        return authorDao.save(author);
    }

    @Override
    public void delete(Long id) {
        authorDao.delete(id);
    }

    @Override
    public void update(Long id, Author author) {
        authorDao.update(id, author);
    }
}
