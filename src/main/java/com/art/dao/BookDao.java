package com.art.dao;

import com.art.entity.Author;
import com.art.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> findByAuthorId(Long authorId);
}
