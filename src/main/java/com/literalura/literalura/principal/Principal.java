package com.literalura.literalura.principal;

import com.literalura.literalura.model.ConverteDados;
import com.literalura.literalura.model.DadosLivro;
import com.literalura.literalura.model.Livro;
import com.literalura.literalura.repository.AuthorRepository;
import com.literalura.literalura.service.ConsumoApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final AuthorRepository repositorio;
    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<Livro> series = new ArrayList<>();

    public Principal(AuthorRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar livro
                    2 - Listar livros buscadas
                    3 - Buscar livro por título
                    5 - Buscar livros por ator
                    6 - Top 10 Livros

                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    //listarLivrosBuscados();
                    break;
                case 3:
                    //buscarLivroPorTitulo();
                    break;
                case 4:
                    //buscarLivrosPorAutor();
                    break;
                case 5:
                    //buscarTop10Livros();
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
        DadosLivro dados = getDadosSerie();
        Livro livro = new Livro(dados);
        //dadosSeries.add(dados);
        //repositorio.save(livro);
        System.out.println(dados);
    }

    private DadosLivro getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        DadosLivro dados = conversor.obterDados(json, DadosLivro.class);
        System.out.println(json);
        return dados;
    }
}