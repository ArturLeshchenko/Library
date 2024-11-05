package com.art.service;

import com.art.entity.Author;
import com.art.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }


    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Автор не найден"));
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author update(Long id, Author author) {
        authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Автор не найден"));
        author.setId(id);
        return authorRepository.save(author);
    }
}
