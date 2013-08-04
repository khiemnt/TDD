package com.qsoft.BankAccount.business;

import com.qsoft.BankAccount.persistence.dao.TransactionDAO;
import com.qsoft.BankAccount.persistence.model.TransactionEntity;

import java.util.List;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 11:14 PM
 */
public interface TransactionService
{
    public TransactionDAO getDao();

    void setDao(TransactionDAO mockTransactionDao);

    List<TransactionEntity> getTransactionsOccurred(String accountNumber);

    List<TransactionEntity> getTransactionsOccurred(String accountNumber, long startTime, long stopTime);

    List<TransactionEntity> getTransactionsOccurred(String accountNumber, int n);

    void createTransaction(String accountNumber, int amount, String description);

}
