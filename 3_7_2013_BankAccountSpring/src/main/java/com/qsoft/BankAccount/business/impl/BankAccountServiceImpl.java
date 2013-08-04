package com.qsoft.BankAccount.business.impl;

import com.qsoft.BankAccount.business.BankAccountService;
import com.qsoft.BankAccount.persistence.model.BankAccountEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 2:19 PM
 */

public class BankAccountServiceImpl implements BankAccountService
{
    @Autowired
    private BankAccountDAO bankAccountDAO;

    public void setDao(BankAccountDAO bankAccountDAO)
    {
        this.bankAccountDAO = bankAccountDAO;
    }

    public BankAccountDAO getDao()
    {
        return bankAccountDAO;
    }

    public BankAccountEntity open(String accountNumber) throws Exception
    {
        BankAccountEntity bankAccountEntity = new BankAccountEntity(accountNumber, 0);
        bankAccountDAO.save(bankAccountEntity);
        return bankAccountEntity;
    }

    @Override
    public BankAccountEntity getAccount(String accountNumber) throws Exception
    {
        System.out.println(bankAccountDAO);
        return bankAccountDAO.getAccount(accountNumber);
    }

    @Override
    public void deposit(String accountNumber, int amount, String description) throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void withdraw(String accountNumber, int amount, String description) throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}
