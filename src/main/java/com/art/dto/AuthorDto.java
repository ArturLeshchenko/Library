package com.art.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate deathDate;
    private List <BookDtoShort> books;


    public AuthorDto(Long id, String firstName, String lastName, String middleName, LocalDate birthDate, LocalDate deathDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }
}
