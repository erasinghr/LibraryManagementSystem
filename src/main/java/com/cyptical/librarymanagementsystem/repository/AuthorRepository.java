package com.cyptical.librarymanagementsystem.repository;

import com.cyptical.librarymanagementsystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findByEmail(String email);
    List<Author> findByAgeGreaterThanEqualAndCountryAndNameStartingWith(int age, String country, String prefix);
    @Query(value="select a from Author a where a.email = ?1")
    public Author getAuthorGivenEmailIdJPQL(String author_email);
    @Query(value="select a from Author a where a.country = ?1")
    public List<Author> getAuthorByCountryJPQL(String country);
}
