package com.literalura.literalura.service;

import com.literalura.literalura.model.Author;
import com.literalura.literalura.model.Book;
import com.literalura.literalura.model.DataBook;
import com.literalura.literalura.model.DataConverter;
import com.literalura.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private Scanner leitura = new Scanner(System.in);
    private ApiConsumer consumo = new ApiConsumer();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private BookRepository repositorioBook;
    private List<Book> books = new ArrayList<>();
    static Optional<Book> bookSearched;

    @Autowired
    public BookService(BookRepository repositorioBook) {
        this.repositorioBook = repositorioBook;
    }

    public void buscarLivro() {
        System.out.println("Insira o nome do livro que você deseja procurar");
        var nomeLivro = leitura.nextLine();

        // Busca o livro usando o nome fornecido.
        buscarDadosLivro(nomeLivro);
    }

    // Busca o livro na Api
    private void buscarLivroWeb(String nomeLivro) {
        // Verifica novamente se o livro já está salvo no banco de dados
        if (bookSearched.isPresent()) {
            return;
        }

        // Obtem dados do livro da Api
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));

        DataConverter conversor = new DataConverter();
        DataBook dadosLivro = conversor.obterDados(json, DataBook.class);

        if (dadosLivro != null && dadosLivro.results() != null && !dadosLivro.results().isEmpty()) {
            Book primeiroLivro = dadosLivro.results().get(0);

            // Seleciona apenas o primeiro idioma
            if (primeiroLivro.getLanguages() != null && !primeiroLivro.getLanguages().isEmpty()) {
                List<String> idiomas = new ArrayList<>();
                idiomas.add(primeiroLivro.getLanguages().get(0));
                primeiroLivro.setLanguages(idiomas);
            }

            // Seleciona apenas o primeiro autor
            if(primeiroLivro.getAuthors() != null && !primeiroLivro.getAuthors().isEmpty()) {
                List<Author> authors = new ArrayList<>();
                authors.add(primeiroLivro.getAuthors().get(0));
                primeiroLivro.setAuthors(authors);
            }

            // Associar o livro aos autores
            for (Author author : primeiroLivro.getAuthors()) {
                author.getBooks().add(primeiroLivro);
            }

            // Salva o livro no banco de dados
            repositorioBook.save(primeiroLivro);
            // Imprime o Livro da pesquisa da Api
            System.out.println(primeiroLivro);
        } else {
            System.out.println("Nenhum livro encontrado!");
        }
    }

    // Busca o livro no banco de dados
    public void buscarDadosLivro(String nomeLivro) {
        // Verifica se tem o livro no banco de dados
        bookSearched = repositorioBook.findByTitleContainingIgnoreCase(nomeLivro);
        if (bookSearched.isPresent()) {
            Book bookFound = bookSearched.get();
            System.out.println(bookFound);
        } else {
            // Chama o método para buscarLivroWeb, se não tem o livro no banco de dados.
            buscarLivroWeb(nomeLivro);
        }
    }

    // Lista os livros do banco de dados
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

    // Lista os livros pelo idioma que o usuário informa
    public void listarLivrosPorIdioma() {
        var menu = """ 
                Insira o idioma para realizar a busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """;
        System.out.println(menu);
        var idiomaLivro = leitura.nextLine();
        books = repositorioBook.findByLanguage(idiomaLivro.toLowerCase());
        if (!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("Não há livros registrados no banco de dados com esse idioma.");
        }
    }

    // Lista os top 10 livros mais baixados, dos livros que estão no banco de dados
    public void top10MaisBaixados() {
        books = repositorioBook.findTop10ByOrderByDownloadsDesc();
        books.forEach(System.out::println);
    }

    // Busca e imprime o total de livros registrados no banco de dados
    public void totalDeLivros() {
        books = repositorioBook.findAll().stream()
                    .sorted(Comparator.comparing(Book::getTitle))
                    .collect(Collectors.toList());
        if (!books.isEmpty()) {
            System.out.println("Total de livros registrados: " + books.size());
        } else {
            System.out.println("Não há livros registrados no banco de dados.");
        }
    }

    // Busca e imprime os livros por total de Downloads em ordem decrecente
    public void livrosMaisBaixados() {
        books = repositorioBook.findAll().stream()
                .sorted(Comparator.comparing(Book::getDownloads).reversed())
                .collect(Collectors.toList());
        books.forEach(System.out::println);
    }
}
