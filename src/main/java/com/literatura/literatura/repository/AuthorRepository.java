package com.literatura.literatura.repository;

import com.literatura.literatura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a JOIN FETCH a.books")
    List<Author> findAllAuthorsWithBooks();

    @Query("SELECT a FROM Author a WHERE a.birth_year <= ?1 AND (a.death_year >= ?1 OR a.death_year IS NULL)")
    List<Author> findAllAuthorsAliveInYear(Integer year);
}
