package com.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "authors")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonAlias("name") private String name;
    @JsonAlias("birth_year")private int birth_year;
    @JsonAlias("death_year")private int death_year;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author(){}

    // getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String toString() {
        String booksString = books == null ? "Nenhum livro registrado." :
                books.stream()
                        .map(Book::getTitle)
                        .collect(Collectors.joining(", "));

        return "Author: " + name + "\n" +
                "Ano de nascimento: " + birth_year + "\n" +
                "Ano de falecimento: " + death_year + "\n" +
                "Livros: " + booksString +  "\n";
    }
}
