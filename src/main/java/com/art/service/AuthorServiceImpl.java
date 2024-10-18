package com.art.service;

import com.art.dao.AuthorDao;
import com.art.dao.BookDao;
import com.art.entity.Author;
import com.art.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Override
    public List<Author> findAll() {
        return authorDao.findAll()
                .stream()
                .peek(author -> author.setBooks(bookDao.findByAuthorId(author.getId())))
                .collect(Collectors.toList());
    }


    @Override
    public Author findById(Long id) {
         Author author = authorDao.findById(id).orElseThrow(() -> new RuntimeException("Автор не найден"));
         author.setBooks(bookDao.findByAuthorId(author.getId()));
        return author;
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
    public Author update(Long id, Author author) {
        authorDao.findById(id).orElseThrow(() -> new RuntimeException("Автор не найден"));
        return authorDao.update(id, author);
    }
}
