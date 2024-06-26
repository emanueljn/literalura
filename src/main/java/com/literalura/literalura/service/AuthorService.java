package com.literalura.literalura.service;

import com.literalura.literalura.model.Author;
import com.literalura.literalura.repository.AuthorRepository;

import java.util.*;

public class AuthorService {

    private AuthorRepository repositorioAuthor;
    private Scanner leitura = new Scanner(System.in);
    private List<Author> authors = new ArrayList<>();

    public AuthorService(AuthorRepository repositorioAuthor) {
        this.repositorioAuthor = repositorioAuthor;
    }

    public void buscarAutoresRegistrados() {
        authors = repositorioAuthor.findAll();
        authors.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    public void buscarAutoresPorAno() {
        System.out.println("Digite o ano, para busca dos autores vivos nessa data.");
        var ano = leitura.nextInt();
        List<Author> authors = repositorioAuthor.procurarAutorPorAno(ano);
        if(!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("Não encontramos registros de autores vivos na data informada.");
        }
    }

    public void buscarAutorPorParteDoNome() {
        System.out.println("Digite o nome ou parte do nome do autor que você quer pesquisar");
        var authorName = leitura.nextLine();
        authors = repositorioAuthor.AutorPorParteDoNome(authorName);
        if(!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
        System.out.println("Não há resultado para os dados informados!");
    }
    }
}
