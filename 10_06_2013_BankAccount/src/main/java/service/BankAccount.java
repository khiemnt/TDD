package service;

import dao.BankAccountDao;
import dao.TransactionDao;
import dto.BankAccountDTO;
import dto.TransactionDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/10/13
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {
    private static BankAccountDao bankAccountDao;
    private  static TransactionDao transactionDao;
    public static BankAccountDTO openAccount(String accountNumber) {
        BankAccountDTO account=new BankAccountDTO(accountNumber);
        bankAccountDao.save(account)   ;
        return account;
    }

    public static void setBankAccountDao(BankAccountDao bankAccountDao) {
        BankAccount.bankAccountDao = bankAccountDao;
    }

    public static BankAccountDTO getAccount(String accountNumber) {
        return bankAccountDao.getAccount(accountNumber);
    }

    public static void deposit(String accountNumber, int amount, String description) {

        BankAccountDTO accountFromDB=BankAccount.getAccount(accountNumber);
        accountFromDB.setBalance(accountFromDB.getBalance()+amount);
        bankAccountDao.save(accountFromDB) ;
        Transaction.createTransaction(accountNumber,amount,description);
    }

    public static void withdraw(String accountNumber, int amount, String description) {

        BankAccountDTO accountFromDB=BankAccount.getAccount(accountNumber);
        if(accountFromDB.getBalance()>=amount){
        accountFromDB.setBalance(accountFromDB.getBalance()-amount);
        bankAccountDao.save(accountFromDB) ;
        Transaction.createTransaction(accountNumber,amount,description);
        }else{
            throw new NumberFormatException("Withdraw money greater principal!")  ;
        }
    }


    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
        return Transaction.getTransactionsOccurred(accountNumber);
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) {
        return Transaction.getTransactionsOccurred(accountNumber, startTime, stopTime);
    }
}
