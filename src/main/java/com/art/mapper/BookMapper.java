package com.art.mapper;

import com.art.dto.AuthorDto;
import com.art.dto.AuthorDtoShort;
import com.art.dto.BookDto;
import com.art.entity.Author;
import com.art.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookDto mapToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());

        Author author = book.getAuthor();
        AuthorDtoShort authorDto = new AuthorDtoShort(
                author.getId() ,
                author.getFirstName(),
                author.getLastName(),
                author.getMiddleName(),
                author.getBirthDate(),
                author.getDeathDate()
        );
        bookDto.setAuthor(authorDto);
        return bookDto;
    }
}
