package com.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    private List<String> languages;
    @JsonAlias("download_count") private int downloads;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors = new ArrayList<>();

    // Construtor
    public Book() {
        this.languages = new ArrayList<>();
    }

    // getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addLanguage(String language) {
        this.languages.add(language);
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        // Verifica se a lista de idiomas não está vazia e define apenas o primeiro idioma
        if (languages != null && !languages.isEmpty()) {
            this.languages.clear(); // Limpa a lista atual de idiomas
            this.languages.add(languages.get(0)); // Adiciona apenas o primeiro idioma
        }
    }
    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    // Métodos auxiliares para facilitar o acesso aos autores e idiomas
    public String getAuthorNames() {
        StringBuilder sb = new StringBuilder();
        if (authors != null) {
            for (Author author : authors) {
                sb.append(author.getName()).append(", ");
            }
            // Remove a última vírgula e espaço adicionados
            if (sb.length() > 2) {
                sb.setLength(sb.length() - 2);
            }
        }
        return sb.toString();
    }

    public String getFirstLanguage() {
        if (languages != null && !languages.isEmpty()) {
            return languages.get(0);
        } return "Linguagem indefinida!";
    }

    @Override
    public String toString() {
        String authorsNames = authors.stream()
                .map(Author::getName)
                .collect(Collectors.joining(", "));

        return  "---- LIVRO ----" +
                "\nLivro= " + title +
                "\nAutores=" + authorsNames +
                "\nIdiomas= " + (languages.isEmpty() ? "Linguagem indefinida!" : languages.get(0)) +
                "\nTotal de downloads=" + downloads +
                "\n---------------\n";
    }
}