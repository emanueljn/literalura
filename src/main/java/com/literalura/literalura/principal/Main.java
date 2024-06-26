package com.literalura.literalura.principal;

import com.literalura.literalura.model.*;
import com.literalura.literalura.repository.AuthorRepository;
import com.literalura.literalura.repository.BookRepository;
import com.literalura.literalura.service.ApiConsumer;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class Main {

    private Scanner leitura = new Scanner(System.in);
    private ApiConsumer consumo = new ApiConsumer();
    private DataConverter conversor = new DataConverter();
    private final String ENDERECO = "https://gutendex.com/books/?search=";


    private final BookRepository repositorioBook;
    private final AuthorRepository repositorioAuthor;
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    Optional<Author> authorSearched;
    Optional<Book> bookSearched;

    public Main(BookRepository repositorioBook, AuthorRepository repositorioAutor) {
        this.repositorioBook = repositorioBook;
        this.repositorioAuthor = repositorioAutor;
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
                    buscarLivro();
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

    private void buscarLivro() {
        System.out.println("Insira o nome do livro que você deseja procurar");
        var nomeLivro = leitura.nextLine();

        // Busca o livro usando o nome fornecido.
        buscarDadosLivro(nomeLivro);
    }

    private void buscarLivroWeb(String nomeLivro) {
        // Obter dados do livro da web
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));

        DataConverter conversor = new DataConverter();
        DataBook dadosLivro = conversor.obterDados(json, DataBook.class);

        if (dadosLivro != null && dadosLivro.results() != null && !dadosLivro.results().isEmpty()) {
            Book primeiroLivro = dadosLivro.results().get(0);
            // Associar o livro aos autores (supondo que o objeto DataBook já esteja configurado corretamente)
            for (Author author : primeiroLivro.getAuthors()) {
                author.getBooks().add(primeiroLivro);
            }
            repositorioBook.save(primeiroLivro);
            System.out.println(primeiroLivro);
        } else {
            System.out.println("Nenhum livro encontrado.");
        }
    }

    private void buscarDadosLivro(String nomeLivro) {
        bookSearched = repositorioBook.findByTitleContainingIgnoreCase(nomeLivro);
        if (bookSearched.isPresent()) {
            Book bookFound = bookSearched.get();
            System.out.println(bookFound);
        } else {
            buscarLivroWeb(nomeLivro);
        }
    }


    private void listarLivrosRegistrados() {
        List<Book> books = repositorioBook.findAll();

        if (books.isEmpty()) {
            System.out.println("Não há livros registrados no banco de dados.");
        } else {
            books.stream()
                    .sorted(Comparator.comparing(Book::getAuthorNames))
                    .forEach(System.out::println);
        }
    }

    private void buscarAutoresRegistrados() {
        authors = repositorioAuthor.findAll();
        authors.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    private void buscarAutoresPorAno() {
        System.out.println("Digite o ano, para busca dos autores vivos nessa data.");
        var ano = leitura.nextInt();
        List<Author> authors = repositorioAuthor.procurarAutorPorAno(ano);
        if(!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("Não encontramos registros de autores vivos na data informada.");
        }
    }

    private void listarLivrosPorIdioma() {

    }

}