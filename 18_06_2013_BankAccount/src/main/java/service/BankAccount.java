package service;

import dao.BankAccountDao;
import dao.TransactionDao;
import dto.BankAccountDTO;
import dto.TransactionDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/18/13
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {
    private static BankAccountDao bankAccountDao;
    private static TransactionDao transactionDao;

    public static BankAccountDTO openAccount(String accountNumber)
    {
        BankAccountDTO account = new BankAccountDTO(accountNumber);
        bankAccountDao.save(account);
        return account;
    }

    public static void setBankAccountDao(BankAccountDao bankAccountDao) {
        BankAccount.bankAccountDao = bankAccountDao;
    }

    public static BankAccountDao getBankAccountDao() {
        return bankAccountDao;
    }

    public static BankAccountDTO getAccount(String accountNumber) {
        return bankAccountDao.getAccount(accountNumber);
    }

    public static void deposit(String accountNumber, int amount, String description) throws Exception
    {
        BankAccountDTO account = bankAccountDao.getAccount(accountNumber);
        account.setBalance(account.getBalance() + amount);
        bankAccountDao.save(account);

        Transaction.createTransaction(accountNumber, amount, description);
    }

    public static void withdraw(String accountNumber, int amount, String description) throws Exception
    {
        BankAccountDTO account = bankAccountDao.getAccount(accountNumber);
        if (account.getBalance() > amount) {
            account.setBalance(account.getBalance() - amount);
            bankAccountDao.save(account);
            Transaction.createTransaction(accountNumber, amount, description);
        } else {
            throw new RuntimeException("Withdraw fail,Try again!");
        }
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) throws Exception
    {
        return Transaction.getTransactionsOccurred( accountNumber);
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) throws Exception {
        return Transaction.getTransactionsOccurred(accountNumber,startTime,stopTime);
    }


}
