package com.literatura.literatura;

import com.literatura.literatura.repository.AuthorRepository;
import com.literatura.literatura.repository.BookRepository;
import com.literatura.literatura.view.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Menu menu = new Menu(bookRepository, authorRepository);
		menu.mainMenu();
	}

}
