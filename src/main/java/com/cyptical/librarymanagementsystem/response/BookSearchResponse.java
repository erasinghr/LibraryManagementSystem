package com.cyptical.librarymanagementsystem.response;


import com.cyptical.librarymanagementsystem.models.Author;
import com.cyptical.librarymanagementsystem.models.Genre;
import com.cyptical.librarymanagementsystem.models.Student;
import com.cyptical.librarymanagementsystem.models.Transaction;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSearchResponse {
    private int id;
    private String name;
    private int cost;
    private Genre genre;

    private Author author;
    private Student student;
    private List<Transaction> transactionList;
    private Date createdOn;
    private Date updatedOn;
}
