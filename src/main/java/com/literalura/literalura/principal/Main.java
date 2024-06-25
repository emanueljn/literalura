package com.literalura.literalura.principal;

import com.literalura.literalura.model.*;
import com.literalura.literalura.repository.AuthorRepository;
import com.literalura.literalura.repository.BookRepository;
import com.literalura.literalura.service.ApiConsumer;

import java.util.*;

public class Main {

    private Scanner leitura = new Scanner(System.in);
    private ApiConsumer consumo = new ApiConsumer();
    private DataConverter conversor = new DataConverter();
    private final String ENDERECO = "https://gutendex.com/books/?search=";


    private final BookRepository repositorio;
    private final AuthorRepository repositorioAutor;
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    Optional<Book> bookSearched;

    public Main(BookRepository repositorio, AuthorRepository repositorioAutor) {
        this.repositorio = repositorio;
        this.repositorioAutor = repositorioAutor;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    -----------------
                    Escolha o número de sua opção:
                    1 - buscar livro pelo título
                    2 - listar livros registrados
                    3 - buscar autores registrados
                    4 - buscar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma

                    0 - sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    buscarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;

            }
        }
    }

    private void buscarLivroWeb() {
        DataBook dadosLivro = buscarDadosLivro();

        if (dadosLivro != null && dadosLivro.results() != null && !dadosLivro.results().isEmpty()) {
            Book primeiroLivro = dadosLivro.results().get(0);

            // Associar o livro aos autores (supondo que o objeto DataBook já esteja configurado corretamente)
            for (Author author : primeiroLivro.getAuthors()) {
                author.getBooks().add(primeiroLivro);
            }

            repositorio.save(primeiroLivro);

            System.out.println("---- LIVRO ----");
            System.out.println("Título: " + primeiroLivro.getTitle());

            for (Author author : primeiroLivro.getAuthors()) {
                System.out.println("Autor:" + author.getName());
            }

            System.out.println("Idioma: " + primeiroLivro.getLanguages());

            System.out.println("Número de downloads: " + primeiroLivro.getDownload_count());
            System.out.println("---------------");
        } else {
            System.out.println("Nenhum livro encontrado.");
        }
    }

    private DataBook buscarDadosLivro() {
        System.out.println("Insira o nome do livro que você deseja procurar");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));

        DataConverter conversor = new DataConverter();
        return conversor.obterDados(json, DataBook.class);
    }

    private void listarLivrosRegistrados() {
        books = repositorio.findAll();
        books.stream()
                .sorted(Comparator.comparing(Book::getAuthorNames))
                .forEach(System.out::println);
    }

    private void buscarAutoresRegistrados() {
        authors = repositorioAutor.findAll();
        authors.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    private void buscarAutoresPorAno() {

    }

    private void listarLivrosPorIdioma() {

    }

}