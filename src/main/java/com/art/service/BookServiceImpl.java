package com.art.service;

import com.art.entity.Book;
import com.art.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;



    @Override
    public List<Book> findBooks() {
        return bookRepository.findAll();
    }
}
