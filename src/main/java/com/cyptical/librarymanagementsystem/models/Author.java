package com.cyptical.librarymanagementsystem.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter
    private int id;
    private String name;
    private String country;
    private int age;
    @Column(unique = true, nullable = false)
    private String email;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Book> bookList;
    @CreationTimestamp
    private Date addedOn;
}
