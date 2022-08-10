package com.cyptical.librarymanagementsystem.repository;

import com.cyptical.librarymanagementsystem.models.Book;
import com.cyptical.librarymanagementsystem.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByName(String name);
    List<Book> findByAuthorName(String authorName);
    List<Book> findByGenre(Genre genre);
}
