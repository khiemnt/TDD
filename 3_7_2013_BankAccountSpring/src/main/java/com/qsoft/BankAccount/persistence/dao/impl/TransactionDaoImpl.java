package com.qsoft.BankAccount.persistence.dao.impl;

import com.qsoft.BankAccount.persistence.dao.TransactionDAO;
import com.qsoft.BankAccount.persistence.model.TransactionEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 11:14 PM
 */
public class TransactionDaoImpl implements TransactionDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(TransactionEntity transactionEntity)
    {
        entityManager.persist(transactionEntity);
        entityManager.flush();
    }

    @Override
    public List<TransactionEntity> getTransactionsOccurred(String accountNumber)
    {
        Query query = entityManager.createQuery("select o from TransactionEntity o where o.accountNumber = :_accountNumber", TransactionEntity.class);
        query.setParameter("_accountNumber", accountNumber);
        return query.getResultList();
    }

    @Override
    public List<TransactionEntity> getTransactionsOccurred(String accountNumber, long startTime, long stopTime)
    {
        Query query = entityManager.createQuery("select o from TransactionEntity o where o.accountNumber = :_accountNumber and o.timeStamp >= :_startTime and o.timeStamp <= :_stopTime", TransactionEntity.class);
        query.setParameter("_accountNumber", accountNumber);
        query.setParameter("_startTime", startTime);
        query.setParameter("_stopTime", stopTime);
        return query.getResultList();
    }

    @Override
    public List<TransactionEntity> getTransactionsOccurred(String accountNumber, int n)
    {
        if (n == 0)
            return null;
        Query query = entityManager.createQuery("select o from TransactionEntity o where o.accountNumber = :_accountNumber order by o.timeStamp desc ", TransactionEntity.class);
        query.setMaxResults(n);
        query.setParameter("_accountNumber", accountNumber);
        return query.getResultList();
    }
}
