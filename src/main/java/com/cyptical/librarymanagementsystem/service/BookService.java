package com.cyptical.librarymanagementsystem.service;


import com.cyptical.librarymanagementsystem.models.Author;
import com.cyptical.librarymanagementsystem.models.Book;
import com.cyptical.librarymanagementsystem.models.Genre;
import com.cyptical.librarymanagementsystem.repository.AuthorRepository;
import com.cyptical.librarymanagementsystem.repository.BookRepository;
import com.cyptical.librarymanagementsystem.request.BookCreateRequest;
import com.cyptical.librarymanagementsystem.request.BookFilterType;
import com.cyptical.librarymanagementsystem.request.BookSearchOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    public void createOrUpdate(BookCreateRequest bookCreateRequest){
        Book book = bookCreateRequest.to();
        createOrUpdate(book);
    }
    public void createOrUpdate(Book book){
        Author author = book.getAuthor();
        Author authorFromDB = authorRepository.findByEmail(author.getEmail());
        if(authorFromDB == null){
            authorFromDB = authorRepository.save(author);
        }
        book.setAuthor(authorFromDB);
        bookRepository.save(book);
    }
    public List<Book> find(BookFilterType bookFilterType, String value){

        return switch (bookFilterType) {
            case NAME -> bookRepository.findByName(value);
            case AUTHOR_NAME -> bookRepository.findByAuthorName(value);
            case GENRE -> bookRepository.findByGenre(Genre.valueOf(value));
            case BOOK_ID -> bookRepository
                    .findAllById(Collections.singletonList(Integer.parseInt(value)));
            default -> new ArrayList<>();
        };
    }
}
