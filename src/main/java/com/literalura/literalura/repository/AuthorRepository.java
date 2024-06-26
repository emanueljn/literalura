package com.literalura.literalura.repository;

import com.literalura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a " + "WHERE (a.death_year IS NULL OR a.death_year > :date) " + "AND a.birth_year <= :date")
    List<Author> procurarAutorPorAno(@Param("date") int date);

    @Query("SELECT a FROM Author a WHERE lower(a.name) ILIKE (concat('%', :authorName, '%'))")
    List<Author> AutorPorParteDoNome(String authorName);
}