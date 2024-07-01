package com.literatura.literatura.view;

import com.literatura.literatura.DataApi.Data;
import com.literatura.literatura.DataApi.DataBook;
import com.literatura.literatura.model.Author;
import com.literatura.literatura.model.Book;
import com.literatura.literatura.repository.AuthorRepository;
import com.literatura.literatura.repository.BookRepository;
import com.literatura.literatura.service.ApiConsumer;
import com.literatura.literatura.service.DataConverter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ApiConsumer apiConsumer = new ApiConsumer();
    private final DataConverter dataConverter = new DataConverter();
    private final Scanner scanner = new Scanner(System.in);
    private List<Book> bookList;
    private List<Author> authors;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Menu(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void mainMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1. Search book by title
                    2. List all books
                    3. List all authors
                    4. List all authors alive in a given year
                    5. List all books by language
                    6. List the 3 most downloaded books
                    0. Exit
                    """;
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> searchBookByTitle();
                case 2 -> listAllBooks();
                case 3 -> listAllAuthors();
                case 4 -> listAuthorsAliveInYear();
                case 5 -> listBooksByLanguage();
                case 6 -> listTop3BooksMostDownloaded();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void listTop3BooksMostDownloaded() {
        bookList = bookRepository.findTop3ByOrderByDownloadsDesc();
        bookList.forEach(System.out::println);
    }

    private List<DataBook> getDataBooks() {
        System.out.println("Enter the title of the book: ");
        var title = scanner.nextLine();
        String url = URL_BASE + "?search=" + title.replace(" ", "+");
        var json = apiConsumer.obtainData(url);
        var data = dataConverter.obtainData(json, Data.class);
        return data.dataBooks();
    }

    private void searchBookByTitle() {
        List<DataBook> books = getDataBooks();
        books.stream()
                .map(Book::new)
                .findFirst()
                .ifPresentOrElse(book -> {
                    if (bookRepository.findByTitle(book.getTitle()).isEmpty()) {
                        System.out.println(book);
                        bookRepository.save(book);
                        System.out.println("Book saved");
                    } else {
                        System.out.println("Book already exists");
                    }
                }, () -> System.out.println("No book found"));
    }

    private void listAllBooks() {
        bookList = bookRepository.findAll();
        bookList.forEach(System.out::println);
    }

    private void listAllAuthors() {
        authors = authorRepository.findAllAuthorsWithBooks();
        convertList(authors);
    }

    private void listAuthorsAliveInYear() {
        System.out.println("Enter the year: ");
        var year = scanner.nextInt();
        authors = authorRepository.findAllAuthorsAliveInYear(year);
        convertList(authors);
    }

    private void listBooksByLanguage() {
        String language;
        do {
            System.out.println("Enter the language: ");
            var menu = """
                    en -> English
                    es -> Spanish
                    fr -> French
                    de -> German
                    it -> Italian
                    pt -> Portuguese
                    """;
            System.out.println(menu);
            language = scanner.nextLine();
            switch (language) {
                case "en", "es", "fr", "de", "it", "pt" -> {
                    bookList = bookRepository.findAllBooksByLanguage(language);
                    bookList.forEach(System.out::println);
                    return;
                }
                default -> System.out.println("Invalid language. Please try again.");
            }
        } while (true);
    }

    public void convertList(List<Author> authors) {
        authors.stream()
                .collect(Collectors.groupingBy(Author::getName))
                .forEach((name, authorList) -> {
                    bookList = authorList.stream()
                            .flatMap(author -> author.getBooks().stream())
                            .distinct()
                            .collect(Collectors.toList());
                    Author author = new Author();
                    author.setName(name);
                    author.setBirth_year(authorList.getFirst().getBirth_year());
                    author.setDeath_year(authorList.getFirst().getDeath_year());
                    author.setBooks(bookList);
                    System.out.println(author);
                });
    }
}
