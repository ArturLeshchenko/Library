package com.art.controller;

import com.art.dto.BookDto;
import com.art.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.art.service.BookService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("api/v1/books")
    public List<BookDto> findBooks() {

        return  bookService.findBooks().stream().map(book -> bookMapper.mapToDto(book)).collect(Collectors.toList());
    }

}
