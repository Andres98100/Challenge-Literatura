package com.literatura.literatura.repository;

import com.literatura.literatura.model.Author;
import com.literatura.literatura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.languages LIKE %?1%")
    List<Book> findAllBooksByLanguage(String language);

    Optional<Book> findByTitle(String title);

    // los 3 libros mas descargados
    List<Book> findTop3ByOrderByDownloadsDesc();
}
