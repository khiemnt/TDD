package service;

import dao.BankAccountDao;
import dao.TransactionDao;
import dto.BankAccountDTO;
import dto.TransactionDTO;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/13/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {
    private static BankAccountDao mockAccountDao;
    private static TransactionDao mockTransactionDao;

    public static void setBankAccountDao(BankAccountDao bankAccountDao) {
        BankAccount.mockAccountDao = bankAccountDao;
    }

    public static BankAccountDao getBankAccountDao() {
        return mockAccountDao;
    }


    public static BankAccountDTO openAccount(String accountNumber) {
        BankAccountDTO accountDTO=new BankAccountDTO(accountNumber);
        mockAccountDao.save(accountDTO);
        return accountDTO;
    }


    public static BankAccountDTO getAccount(String accountNumber) {
        return  mockAccountDao.getAccount(accountNumber);
}

    public static void deposit(String accountNumber, double amount, String description) {
        BankAccountDTO account=mockAccountDao.getAccount(accountNumber);
        account.setBalance(account.getBalance()+amount);
        mockAccountDao.save(account);

        Transaction.createTransaction(accountNumber,amount,description);

    }

    public static void withdraw(String accountNumber, double amount, String description) {
        BankAccountDTO account=mockAccountDao.getAccount(accountNumber);
        account.setBalance(account.getBalance()- amount);
        mockAccountDao.save(account);

        Transaction.createTransaction(accountNumber,amount,description);
    }
}
