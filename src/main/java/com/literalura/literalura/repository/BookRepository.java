package com.literalura.literalura.repository;

import com.literalura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleContainingIgnoreCase(String nomeLivro);

    @Query("SELECT b FROM Book b WHERE LOWER(:language) MEMBER OF b.languages")
    List<Book> findByLanguage(@Param("language") String language);

    List<Book> findTop10ByOrderByDownloadsDesc();
}
