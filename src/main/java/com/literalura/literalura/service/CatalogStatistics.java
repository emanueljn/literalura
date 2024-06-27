package com.literalura.literalura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class CatalogStatistics {

    private Scanner leitura = new Scanner(System.in);
    private BookService bookService;
    private AuthorService authorService;

    public CatalogStatistics(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public void exibirEstatisticas() {

        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    -----------------
                    Escolha o número de sua opção:
                    
                    Estatísticas dos Livros:
                    1 - total de livros cadastrados   
                    2 - livro mais baixado
                    
                    Estatísticas dos Autores:
                    3 - total de autores registrados
                    4 - média de livros por autor
                                        

                    0 - sair
                    """;

            System.out.println(menu);
            try {
                opcao = leitura.nextInt();
                leitura.nextLine();

                // Métodos disponíveis para o usuário
                switch (opcao) {
                    case 1:
                        bookService.totalDeLivros();
                        break;
                    case 2:
                        bookService.livrosMaisBaixados();
                        break;
                    case 3:
                        authorService.totalDeAutores();
                        break;
                    case 4:
                        authorService.mediaDeLivrosPorAutor();
                        break;
                    case 0:
                        System.out.println("Saindo...");
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