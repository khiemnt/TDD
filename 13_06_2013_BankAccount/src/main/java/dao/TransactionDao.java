package dao;

import dto.BankAccountDTO;
import dto.TransactionDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/13/13
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionDao {
    public void save(TransactionDTO bankAccountDTO) {

    }

    public List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
        return null;
    }

    public List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) {
        return null;
    }

    public List<TransactionDTO> getTransactionsOccurred(String accountNumber, int numOfTransaction) {
            return null;
    }
}
