package com.art.controller;

import com.art.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.art.service.BookService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("api/v1/books")
    public List<Book> findBooks(@RequestParam int pageSize,
                                @RequestParam int pageNumber) {

        return  bookService.findBooks(pageSize, pageNumber);
    }

}
