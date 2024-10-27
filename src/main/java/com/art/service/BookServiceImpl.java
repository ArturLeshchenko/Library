package com.art.service;

import com.art.dao.BookDao;
import com.art.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;



    @Override
    public List<Book> findBooks(int pageSize, int pageNumber) {
        int offset = pageSize * pageNumber;
        return bookDao.findAll(pageSize, offset);
    }
}
