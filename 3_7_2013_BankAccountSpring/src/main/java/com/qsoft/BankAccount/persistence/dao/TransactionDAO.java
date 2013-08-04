package com.qsoft.BankAccount.persistence.dao;

import com.qsoft.BankAccount.persistence.model.TransactionEntity;

import java.util.List;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 11:14 PM
 */
public interface TransactionDAO
{
        void save(TransactionEntity transactionEntity);

        List<TransactionEntity> getTransactionsOccurred(String accountNumber);

        List<TransactionEntity> getTransactionsOccurred(String accountNumber, long startTime, long stopTime);

        List<TransactionEntity> getTransactionsOccurred(String accountNumber, int n);
}
