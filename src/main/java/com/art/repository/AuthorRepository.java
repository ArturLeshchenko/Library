package com.art.repository;

import com.art.entity.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "select author from Author author join fetch author.books where author.id =:id")
    Optional<Author> findByIdWithBooks(Long id);

    Optional<Author> findByFirstName(String firstName);
}
