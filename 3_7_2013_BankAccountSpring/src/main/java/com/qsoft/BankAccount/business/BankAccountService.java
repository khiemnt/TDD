package com.qsoft.BankAccount.business;

import com.qsoft.BankAccount.persistence.model.BankAccountEntity;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 2:19 PM
 */
public interface BankAccountService
{
    public BankAccountDAO getDao();

    public void setDao(BankAccountDAO bankAccountDAO);

    public BankAccountEntity open(String accountNumber) throws Exception;

    public BankAccountEntity getAccount(String accountNumber) throws Exception;

    public void deposit(String accountNumber, int amount, String description) throws Exception;

    public void withdraw(String accountNumber, int amount, String description) throws Exception;

}
