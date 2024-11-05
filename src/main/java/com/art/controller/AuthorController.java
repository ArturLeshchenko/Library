package com.art.controller;

import com.art.dto.AuthorDto;
import com.art.entity.Author;
import com.art.mapper.AuthorMapper;
import com.art.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping("/api/v1/authors")
    public List<AuthorDto> findAll() {
        return authorService.findAll().stream().map(author -> authorMapper.mapToDto(author)).collect(Collectors.toList());
    }

    @GetMapping("/api/v1/author/{id}")
    public Author findById(@PathVariable(name = "id") Long id) {
        return authorService.findById(id);
    }

    @PostMapping("/api/v1/author")
    public Author save(@RequestBody Author author) {
        return authorService.save(author);
    }

    @DeleteMapping("/api/v1/author/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        authorService.delete(id);
    }

    @PutMapping("/api/v1/author/{id}")
    public Author update(@PathVariable Long id, @RequestBody Author author) {
        return authorService.update(id, author);
    }
}
