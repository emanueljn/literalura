package com.literalura.literalura.principal;

import com.literalura.literalura.service.AuthorService;
import com.literalura.literalura.service.BookService;
import com.literalura.literalura.service.CatalogStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Main {

    private Scanner leitura = new Scanner(System.in);
    private BookService bookService;
    private AuthorService authorService;
    private CatalogStatistics catalogStatistics;

    @Autowired
    public Main(BookService bookService, AuthorService authorService, CatalogStatistics catalogStatistics) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.catalogStatistics = catalogStatistics;
        this.leitura = new Scanner(System.in);
    }

    // Exibi o meu para o usuário
    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    -----------------
                    Escolha o número de sua opção:
                    1 - buscar livro pelo título
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                    6 - listar top 10 livros mais baixados
                    7 - buscar autor por trecho do nome
                    8 - exibir estatísticas
                    

                    0 - sair
                    """;

            System.out.println(menu);
            try {
                opcao = leitura.nextInt();
                leitura.nextLine();

                // Métodos disponíveis para o usuário
                switch (opcao) {
                    case 1:
                        bookService.buscarLivro();
                        break;
                    case 2:
                        bookService.listarLivrosRegistrados();
                        break;
                    case 3:
                        authorService.listarAutoresRegistrados();
                        break;
                    case 4:
                        authorService.buscarAutoresPorAno();
                        break;
                    case 5:
                        bookService.listarLivrosPorIdioma();
                        break;
                    case 6:
                        bookService.top10MaisBaixados();
                        break;
                    case 7:
                        authorService.buscarAutoresPorParteDoNome();
                        break;
                    case 8:
                        catalogStatistics.exibirEstatisticas();
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;

                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
                leitura.nextLine(); // Limpa o buffer em caso de erro
            }

        }
    }
}