package com.cyptical.librarymanagementsystem.service;

import com.cyptical.librarymanagementsystem.exceptions.TxnServiceException;
import com.cyptical.librarymanagementsystem.models.Book;
import com.cyptical.librarymanagementsystem.models.Student;
import com.cyptical.librarymanagementsystem.models.Transaction;
import com.cyptical.librarymanagementsystem.models.TransactionType;
import com.cyptical.librarymanagementsystem.repository.TransactionRepository;
import com.cyptical.librarymanagementsystem.request.BookFilterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    StudentService studentService;
    @Autowired
    BookService bookService;
    @Autowired
    TransactionRepository transactionRepository;
    @Value("${book.return.due_date}")
    int numberOfDays;

    @Transactional
    public String issueTxn(int studentId, int bookId) throws TxnServiceException {
        /*
         * Student is a valid entity
         * Book is present and is available
         * Create a transaction --> saving in the txn table
         * Make the book unavailable
         */

        Student student = studentService.findStudentByStudentId(studentId);

        if(student == null){
            throw new TxnServiceException("Student not present in the library");
        }

        List<Book> books = bookService.find(BookFilterType.BOOK_ID, String.valueOf(bookId));
        if(books == null || books.size() != 1 || books.get(0).getStudent() != null){
            throw new TxnServiceException("Book not present in the library");
        }

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .payment(books.get(0).getCost())
                .book(books.get(0))
                .student(student)
                .build();

        transactionRepository.save(transaction);

        //

        books.get(0).setStudent(student);
        bookService.createOrUpdate(books.get(0));

        return transaction.getExternalTxnId();

    }

    public String returnTxn(int studentId, int bookId) throws TxnServiceException {

        /*
         * Student is a valid entity
         * Book is issued to this particular student
         * Calculate the fine
         * Create a transaction --> saving in the txn table
         * Make the book available
         */


        Student student = studentService.findStudentByStudentId(studentId);

        if(student == null){
            throw new TxnServiceException("Student not present in the library");
        }

        List<Book> books = bookService.find(BookFilterType.BOOK_ID, String.valueOf(bookId));
        if(books == null || books.size() != 1){
            throw new TxnServiceException("Book not present in the library");
        }
        int fetchedId = Integer.parseInt(books.get(0).getStudent().getId());
        if(fetchedId != studentId){
            throw new TxnServiceException("Book not issued to this student");
        }

        // Finding the original issue Txn

        Transaction issueTxn = transactionRepository
                .findTopByBookAndStudentAndTransactionTypeOrderByIdDesc(books.get(0), student, TransactionType.ISSUE);

        logger.info("issueTxn date - {}, txnId - {}", issueTxn.getTransactionDate(), issueTxn.getId());

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN)
                .externalTxnId(UUID.randomUUID().toString())
                .book(books.get(0))
                .student(student)
                .payment(calculateFine(issueTxn))
                .build();

        transactionRepository.save(transaction);

        books.get(0).setStudent(null);
        bookService.createOrUpdate(books.get(0));
        return transaction.getExternalTxnId();
    }

    private double calculateFine(Transaction issueTxn){
        long issueTime = issueTxn.getTransactionDate().getTime();
        long returnTime = System.currentTimeMillis();

        long diff = returnTime - issueTime;
        long daysPassed = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);


        if(daysPassed >= numberOfDays) {
            return (daysPassed - numberOfDays) * 1.0;
        }

        return 0.0;
    }
}
