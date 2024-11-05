package com.art.controller;

import com.art.dto.AuthorDto;
import com.art.dto.AuthorDtoShort;
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
    public List<AuthorDtoShort> findAll() {
        return authorService.findAll().stream().map(author -> authorMapper.mapToDtoShort(author)).collect(Collectors.toList());
    }

    @GetMapping("/api/v1/author/{id}")
    public AuthorDto findById(@PathVariable(name = "id") Long id) {
        return authorMapper.mapToDto(authorService.findById(id));
    }

    @PostMapping("/api/v1/author")
    public AuthorDtoShort save(@RequestBody AuthorDtoShort authorDtoShort) {
        Author author = authorService.save(authorMapper.mapToEntity(authorDtoShort));
        return authorMapper.mapToDtoShort(author);
    }

    @DeleteMapping("/api/v1/author/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        authorService.delete(id);
    }

    @PutMapping("/api/v1/author/{id}")
    public AuthorDtoShort update(@PathVariable Long id, @RequestBody AuthorDtoShort authorDtoShort) {
        Author author = authorService.update(id, authorMapper.mapToEntity(authorDtoShort));
        return authorMapper.mapToDtoShort(author);
    }
}
