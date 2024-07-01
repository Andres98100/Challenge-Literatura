package com.literatura.literatura.model;


import com.literatura.literatura.DataApi.DataAuthor;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birth_year;
    private Integer death_year;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {}

    public Author(DataAuthor dataAuthor) {
        this.name = dataAuthor.name();
        this.birth_year = dataAuthor.birthYear();
        this.death_year = dataAuthor.deathYear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        var bookTitles = books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining(", "));
        return """
                ------- Author -------
                
                Name: %s
                Birth year: %d
                Death year: %d
                Books: [%s]
                
                ---------------------
                """.formatted(name, birth_year, death_year, bookTitles);
    }
}
