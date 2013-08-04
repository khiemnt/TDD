package dao;

import dto.TransactionDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/19/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionDao {
    private final Connection dbConnection;
    public TransactionDao(DataSource dataSource) throws SQLException
    {
        this.dbConnection = dataSource.getConnection();
    }

    public void save(TransactionDTO transactionDTO) throws Exception
    {
        String query = "INSERT INTO TRANSACTION VALUES('"+transactionDTO.getAccountNumber()+"','"+transactionDTO.getTimeStamp()+"',"+transactionDTO.getAmount()+",'"+transactionDTO.getDescription()+"')";
        dbConnection.createStatement().execute(query);
    }

    public List<TransactionDTO> getTransactionsOccurred(String accountNumber) throws Exception
    {
        String query = "SELECT * FROM TRANSACTION WHERE account_number='"+accountNumber+"'";
        ResultSet resultSet = dbConnection.createStatement().executeQuery(query);
        List<TransactionDTO> tempList = new ArrayList<TransactionDTO>();
        while (resultSet.next()) {
            tempList.add(new TransactionDTO(accountNumber, resultSet.getLong("timestamp"), resultSet.getDouble("amount"), resultSet.getString("description")));
        }
        return tempList;
    }

    public List<TransactionDTO> getTransactionsOccurred(String accountNumber, long startTime, long stopTime) throws Exception{
        String queryString = "SELECT * FROM TRANSACTION WHERE ACCOUNT_NUMBER='" +
                accountNumber + "' AND TIMESTAMP >=" + startTime + "AND TIMESTAMP <= " + stopTime;
        ResultSet resultSet = dbConnection.createStatement().executeQuery(queryString);
        List<TransactionDTO> tempList = new ArrayList<TransactionDTO>();
        while (resultSet.next()) {
            tempList.add(new TransactionDTO(accountNumber, resultSet.getLong("timestamp"), resultSet.getDouble("amount"), resultSet.getString("description")));
        }
        return tempList;
    }

    public List<TransactionDTO> getTransactionsOccurred(String accountNumber, int numOfTransaction) throws Exception
    {
        String queryString = "SELECT * FROM TRANSACTION WHERE ACCOUNT_NUMBER='" +
                accountNumber + "' ORDER BY timestamp DESC limit " + numOfTransaction;
        ResultSet resultSet = dbConnection.createStatement().executeQuery(queryString);
        List<TransactionDTO> tempList = new ArrayList<TransactionDTO>();
        while (resultSet.next()) {
            tempList.add(new TransactionDTO(accountNumber, resultSet.getLong("timestamp"), resultSet.getDouble("amount"), resultSet.getString("description")));
        }
        return tempList;
    }
}
