package com.cyptical.librarymanagementsystem.repository;

import com.cyptical.librarymanagementsystem.models.Book;
import com.cyptical.librarymanagementsystem.models.Student;
import com.cyptical.librarymanagementsystem.models.Transaction;
import com.cyptical.librarymanagementsystem.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findTopByBookAndStudentAndTransactionTypeOrderByIdDesc(Book book, Student student, TransactionType transactionType);
}
