package service;

import dao.TransactionDao;
import dto.TransactionDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/10/13
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {
    private static TransactionDao transactionDao;

    public static TransactionDTO createTransaction(String accountNumber, double amount, String description) {
        long timeStamp=System.currentTimeMillis();
        TransactionDTO transactionDTO=new TransactionDTO(accountNumber,amount,description);
        transactionDao.save(transactionDTO);
        return transactionDTO;
    }

    public static void setTransactionDao(TransactionDao transactionDao) {
        Transaction.transactionDao = transactionDao;
    }

    public static TransactionDao getTransactionDao() {
        return transactionDao;
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
        return transactionDao.getTransactionsOccurred(accountNumber);
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) {
        return transactionDao.getTransactionsOccurred(accountNumber,startTime,stopTime);
    }

    public static List<TransactionDTO> GetNewTransactionToCurrentTime(String accountNumber, int numOfTransaction) {
        return transactionDao.getTransactionsOccurredNewTimes(accountNumber,numOfTransaction);
    }
}
