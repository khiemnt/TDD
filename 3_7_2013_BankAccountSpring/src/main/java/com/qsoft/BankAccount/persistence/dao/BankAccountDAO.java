package com.qsoft.BankAccount.persistence.dao;

import com.qsoft.BankAccount.persistence.model.BankAccountEntity;

/**
 * Created with IntelliJ IDEA.
 * User: khiemNT
 * Date: 8/4/13
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BankAccountDAO {

            public BankAccountEntity getAccount(String accountNumber) ;

            void save(BankAccountEntity bankAccountEntity)throws Exception;

}
