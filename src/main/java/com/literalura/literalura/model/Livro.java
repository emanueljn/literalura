package com.literalura.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idiomas;
    private Integer totalDownloads;
    private String count;

    @ManyToMany
    private List<Author> autores = new ArrayList<>();

    // Construtor
    public Livro() {}

    // Construtor
    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.totalDownloads = dadosLivro.totalDownloads();
        this.count = dadosLivro.count();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getTotalDownloads() {
        return totalDownloads;
    }

    public void setTotalDownloads(Integer totalDownloads) {
        this.totalDownloads = totalDownloads;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Author> getAutores() {
        return autores;
    }

    public void setAutores(List<Author> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Livro= " + titulo + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", totalDownloads=" + totalDownloads +
                ", autores=" + autores;
    }
}

