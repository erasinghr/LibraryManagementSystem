package com.cyptical.librarymanagementsystem.controller;

import com.cyptical.librarymanagementsystem.models.Book;
import com.cyptical.librarymanagementsystem.request.BookCreateRequest;
import com.cyptical.librarymanagementsystem.request.BookFilterType;
import com.cyptical.librarymanagementsystem.request.BookSearchOperationType;
import com.cyptical.librarymanagementsystem.response.BookSearchResponse;
import com.cyptical.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @PostMapping("/book")
    public void createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        bookService.createOrUpdate(bookCreateRequest);
    }

    // GET - filter functionality / search
    @GetMapping("/books/search")
    public List<BookSearchResponse> findBooks(
            @RequestParam("filter")BookFilterType bookFilterType,
            @RequestParam("value") String value){

        return bookService
                .find(bookFilterType, value)
                .stream()
                .map(Book::to)
                .collect(Collectors.toList());
    }
}
