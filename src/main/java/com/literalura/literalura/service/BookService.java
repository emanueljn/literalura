package com.literalura.literalura.service;

import com.literalura.literalura.model.Author;
import com.literalura.literalura.model.Book;
import com.literalura.literalura.model.DataBook;
import com.literalura.literalura.model.DataConverter;
import com.literalura.literalura.repository.BookRepository;

import java.util.*;

public class BookService {

    private Scanner leitura = new Scanner(System.in);
    private ApiConsumer consumo = new ApiConsumer();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private DataConverter conversor = new DataConverter();
    private BookRepository repositorioBook;
    private List<Book> books = new ArrayList<>();
    static Optional<Book> bookSearched;


    public BookService(BookRepository repositorioBook) {
        this.repositorioBook = repositorioBook;
    }

    public void buscarLivro() {
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

            // Configura apenas o primeiro idioma
            if (primeiroLivro.getLanguages() != null && !primeiroLivro.getLanguages().isEmpty()) {
                List<String> idiomas = new ArrayList<>();
                idiomas.add(primeiroLivro.getLanguages().get(0));
                primeiroLivro.setLanguages(idiomas);
            }

            if(primeiroLivro.getAuthors() != null && !primeiroLivro.getAuthors().isEmpty()) {
                List<Author> authors = new ArrayList<>();
                authors.add(primeiroLivro.getAuthors().get(0));
                primeiroLivro.setAuthors(authors);
            }

            // Associar o livro aos autores
            for (Author author : primeiroLivro.getAuthors()) {
                author.getBooks().add(primeiroLivro);
            }

            repositorioBook.save(primeiroLivro);
            System.out.println(primeiroLivro);
        } else {
            System.out.println("Nenhum livro encontrado!");
        }
    }

    public void buscarDadosLivro(String nomeLivro) {
        bookSearched = repositorioBook.findByTitleContainingIgnoreCase(nomeLivro);
        if (bookSearched.isPresent()) {
            Book bookFound = bookSearched.get();
            System.out.println(bookFound);
        } else {
            buscarLivroWeb(nomeLivro);
        }
    }

    public void listarLivrosRegistrados() {
        List<Book> books = repositorioBook.findAll();

        if (books.isEmpty()) {
            System.out.println("Não há livros registrados no banco de dados.");
        } else {
            books.stream()
                    .sorted(Comparator.comparing(Book::getAuthorNames))
                    .forEach(System.out::println);
        }
    }

    public void listarLivrosPorIdioma() {
        System.out.println("Digite o idoma para busca dos: ");
        var idiomaLivro = leitura.nextLine();
        books = repositorioBook.findByLanguage(idiomaLivro.toLowerCase());
        if (!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("Não há livros registrados no banco de dados com esse idioma.");
        }
    }

    public void top10MaisBaixados() {
        books = repositorioBook.findTop10ByOrderByDownloadsDesc();
        books.forEach(System.out::println);
    }
}
