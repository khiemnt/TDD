package com.qsoft.BankAccount.business.impl;

import com.qsoft.BankAccount.business.TransactionService;
import com.qsoft.BankAccount.persistence.dao.TransactionDAO;
import com.qsoft.BankAccount.persistence.model.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 11:14 PM
 */
public class TransactionServiceImpl  implements TransactionService
{
    @Autowired
    private TransactionDAO transactionDAO;

    @Override
    public void setDao(TransactionDAO TransactionDao)
    {
        this.transactionDAO=transactionDAO;
    }

    @Override
    public TransactionDAO getDao()
    {
        return transactionDAO;
    }

    @Override
    public List<TransactionEntity> getTransactionsOccurred(String accountNumber)
    {
        return transactionDAO.getTransactionsOccurred(accountNumber);
    }

    @Override
    public List<TransactionEntity> getTransactionsOccurred(String accountNumber, long startTime, long stopTime)
    {
        return transactionDAO.getTransactionsOccurred(accountNumber,startTime,stopTime);
    }

    @Override
    public List<TransactionEntity> getTransactionsOccurred(String accountNumber, int n)
    {
        return transactionDAO.getTransactionsOccurred(accountNumber,n);
    }

    @Override
    public void createTransaction(String accountNumber, int amount, String description)
    {
        TransactionEntity transactionEntity = new TransactionEntity(accountNumber, amount, description);
        transactionDAO.save(transactionEntity);
    }
}
