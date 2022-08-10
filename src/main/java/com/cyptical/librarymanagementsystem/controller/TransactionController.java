package com.cyptical.librarymanagementsystem.controller;


import com.cyptical.librarymanagementsystem.exceptions.TxnServiceException;
import com.cyptical.librarymanagementsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    // SId - 10 - Piyush
    // SId - 2 - Rohan

    @PostMapping("/transaction/issue")
    public String issueTxn(@RequestParam("studentId") int studentId,
                           @RequestParam("bookId") int bookId) throws TxnServiceException, InterruptedException {
        return transactionService.issueTxn(studentId, bookId);
    }

    @PostMapping("/transaction/return")
    public String returnTxn(@RequestParam("studentId") int studentId,
                            @RequestParam("bookId") int bookId) throws TxnServiceException, InterruptedException {
        return transactionService.returnTxn(studentId, bookId);
    }
}
