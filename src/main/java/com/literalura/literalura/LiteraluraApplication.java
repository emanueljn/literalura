package com.literalura.literalura;

import com.literalura.literalura.principal.Main;
import com.literalura.literalura.repository.AuthorRepository;
import com.literalura.literalura.repository.BookRepository;
import com.literalura.literalura.service.CatalogStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private BookRepository repositorioBook;

	@Autowired
	private AuthorRepository repositorioAuthor;

	@Autowired
	private CatalogStatistics catalogStatistics;

	@Autowired
	private Main main;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		main.exibeMenu();
	}
}
