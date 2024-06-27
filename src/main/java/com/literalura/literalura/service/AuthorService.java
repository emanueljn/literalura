package com.literalura.literalura.service;

import com.literalura.literalura.model.Author;
import com.literalura.literalura.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private AuthorRepository repositorioAuthor;
    private Scanner leitura = new Scanner(System.in);
    private List<Author> authors = new ArrayList<>();

    public AuthorService(AuthorRepository repositorioAuthor) {
        this.repositorioAuthor = repositorioAuthor;
    }

    // Lista os autores registrados
    public void listarAutoresRegistrados() {
        authors = repositorioAuthor.findAll();
        authors.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    // Busca autores por Ano
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

    // Busca autores por parte do nome
    public void buscarAutoresPorParteDoNome() {
        System.out.println("Digite o nome ou parte do nome do autor que você quer pesquisar");
        var authorName = leitura.nextLine();
        authors = repositorioAuthor.AutorPorParteDoNome(authorName);
        if(!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
        System.out.println("Não há resultado para os dados informados!");
    }
    }

    // Busca e imprime o total de autores registrados no banco de dados
    public void totalDeAutores() {
        authors = repositorioAuthor.findAll().stream()
                    .sorted(Comparator.comparing(Author::getName))
                    .collect(Collectors.toList());
        if (!authors.isEmpty()) {
            System.out.println("Total de autores registrados: " + authors.size());
        } else {
            System.out.println("Não há autores registrados no banco de dados.");
        }
    }

    // Busca e imprime a média de livros por autor
    public void mediaDeLivrosPorAutor() {
        DoubleSummaryStatistics stats = repositorioAuthor.findAll().stream()
                .mapToDouble(a -> a.getBooks().size())
                .summaryStatistics();
        double media = stats.getAverage();
        System.out.println("Média de livros por autor: " + media);
    }
}
