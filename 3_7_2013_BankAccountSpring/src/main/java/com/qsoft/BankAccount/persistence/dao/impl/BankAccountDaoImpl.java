package com.qsoft.BankAccount.persistence.dao.impl;

import com.qsoft.BankAccount.persistence.dao.BankAccountDAO;
import com.qsoft.BankAccount.persistence.model.BankAccountEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 1:38 PM
 */
@Transactional
@Component
public class BankAccountDAOImpl implements BankAccountDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BankAccountEntity getAccount(String accountNumber)
    {
        Query query = entityManager.createQuery("select o from BankAccountEntity o where o.accountNumber = :accNumber", BankAccountEntity.class);
        query.setParameter("accNumber", accountNumber);
        List<BankAccountEntity> list = query.getResultList();
        if (list.size() == 0)
        {
            return null;
        }
        else
        {
            return list.get(0);
        }
    }

    @Override
    public void save(BankAccountEntity bankAccountEntity) throws Exception
    {
        if(bankAccountEntity.getBalance()<0){
            throw new  RuntimeException("Balance must greater than 0");

        }
        if (bankAccountEntity.getId() == null)
        {
            entityManager.persist(bankAccountEntity);
        }
        else
        {
            entityManager.merge(bankAccountEntity);
        }
        entityManager.flush();
    }

}
