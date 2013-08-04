package service;

import dao.TransactionDao;
import dto.TransactionDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/13/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {
    private static TransactionDao transactionDao;

    public static void setTransactionDao(TransactionDao transactionDao) {
        Transaction.transactionDao = transactionDao;
    }

    public static TransactionDao getTransactionDao() {
        return transactionDao;
    }


    public static TransactionDTO createTransaction(String accountNumber, double amount, String description) {
        TransactionDTO transactionDTO=new TransactionDTO(accountNumber,amount,description);
        transactionDao.save(transactionDTO);
        return transactionDTO;
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) {

        return transactionDao.getTransactionsOccurred(accountNumber);
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) {
        return transactionDao.getTransactionsOccurred(accountNumber,startTime,stopTime);
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, int numOfTransaction) {
        return transactionDao.getTransactionsOccurred(accountNumber,numOfTransaction);
    }
}
