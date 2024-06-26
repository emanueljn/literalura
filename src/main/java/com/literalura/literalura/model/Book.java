package com.literalura.literalura.model;

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
    private List<String> languages;
    private int download_count;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors = new ArrayList<>();

    // Construtor
    public Book() {}

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

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
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
                "\nIdiomas= " + getFirstLanguage() +
                "\nTotal de downloads=" + download_count +
                "\n---------------\n";
    }
}