package service;

import dao.TransactionDao;
import dto.TransactionDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/19/13
 * Time: 2:00 PM
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

    public static TransactionDTO createTransaction(String accountNumber, int amount, String description) throws Exception
    {
       TransactionDTO transactionDTO=new TransactionDTO(accountNumber,amount,description);
       transactionDao.save(transactionDTO);
       return transactionDTO;
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) throws Exception
    {
        return transactionDao.getTransactionsOccurred(accountNumber);
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) throws Exception {
        return transactionDao.getTransactionsOccurred(accountNumber,startTime,stopTime);
    }

    public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, int numOfTransaction)  throws Exception{
        return transactionDao.getTransactionsOccurred(accountNumber,numOfTransaction);
    }
}
