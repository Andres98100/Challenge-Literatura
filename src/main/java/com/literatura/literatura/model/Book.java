package com.literatura.literatura.model;


import jakarta.persistence.*;
import com.literatura.literatura.DataApi.DataBook;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
    private String languages;
    private Integer downloads;

    public Book() {}

    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        this.languages = String.join(", ", dataBook.lenguages());
        this.downloads = dataBook.downloadCount();

        this.authors = dataBook.authors().stream()
                .map(Author::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return """
                ------- Book -------
                
                Title: %s
                Authors: %s
                Languages: %s
                Downloads: %d
                
                ---------------------
                """.formatted(title, authors.stream().map(Author::getName).collect(Collectors.joining(", ")),
                String.join(", ", languages), downloads);
    }
}
