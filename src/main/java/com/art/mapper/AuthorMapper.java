package com.art.mapper;

import com.art.dto.AuthorDto;
import com.art.dto.AuthorDtoShort;
import com.art.dto.BookDtoShort;
import com.art.entity.Author;
import com.art.entity.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class AuthorMapper {
   public AuthorDto mapToDto (Author author) {
       AuthorDto authorDto = new AuthorDto();
       authorDto.setId(author.getId());
       authorDto.setFirstName(author.getFirstName());
       authorDto.setLastName(author.getLastName());
       authorDto.setMiddleName(author.getMiddleName());
       authorDto.setBirthDate(author.getBirthDate());
       authorDto.setDeathDate(author.getDeathDate());

       List<BookDtoShort> bookDtoShortList = new ArrayList<>();
       for (Book book : author.getBooks()) {
           BookDtoShort bookDtoShort = new BookDtoShort(book.getId(),book.getTitle());
           bookDtoShortList.add(bookDtoShort);
       }
       authorDto.setBooks(bookDtoShortList);
       return authorDto;
   }
   public AuthorDtoShort mapToDtoShort(Author author) {
       AuthorDtoShort authorDtoShort = new AuthorDtoShort();
       authorDtoShort.setId(author.getId());
       authorDtoShort.setFirstName(author.getFirstName());
       authorDtoShort.setLastName(author.getLastName());
       authorDtoShort.setMiddleName(author.getMiddleName());
       authorDtoShort.setBirthDate(author.getBirthDate());
       authorDtoShort.setDeathDate(author.getDeathDate());
       return authorDtoShort;
   }
   public Author mapToEntity(AuthorDtoShort authorDtoShort) {
       Author author = new Author();
       author.setId(authorDtoShort.getId());
       author.setFirstName(authorDtoShort.getFirstName());
       author.setLastName(authorDtoShort.getLastName());
       author.setMiddleName(authorDtoShort.getMiddleName());
       author.setBirthDate(authorDtoShort.getBirthDate());
       author.setDeathDate(authorDtoShort.getDeathDate());
       return author;
   }
}
