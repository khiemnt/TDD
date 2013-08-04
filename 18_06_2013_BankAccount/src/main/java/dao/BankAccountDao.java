package dao;

import dto.BankAccountDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/18/13
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDao
{
    private final Connection dbConnection;

    public BankAccountDao(DataSource dataSource) throws SQLException
    {
        this.dbConnection = dataSource.getConnection();
    }

    public void save(BankAccountDTO accountDTO)
    {
        if(accountDTO.getBalance()<0)
            throw new RuntimeException("Balance must greater than 0");
        try
        {
            String accountNumber = accountDTO.getAccountNumber();
            String queryString = "SELECT * FROM SAVINGS_ACCOUNT WHERE ACCOUNT_NUMBER='" + accountNumber + "'";
            ResultSet resultSet = dbConnection.createStatement().executeQuery(queryString);
            if (resultSet.next())
            {
                String query = "UPDATE SAVINGS_ACCOUNT SET balance = " + accountDTO.getBalance() + " WHERE account_number = '" + accountNumber + "'";
                dbConnection.createStatement().executeUpdate(query);
            }
            else
            {
                String query = "INSERT INTO SAVINGS_ACCOUNT VALUES('" + accountNumber + "'," + accountDTO.getBalance() + "," + accountDTO.getTimeStamp() + ")";
                dbConnection.createStatement().execute(query);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public BankAccountDTO getAccount(String accountNumber)
    {
        String queryString = "SELECT * FROM SAVINGS_ACCOUNT WHERE ACCOUNT_NUMBER='" + accountNumber + "'";
        ResultSet resultSet = null;
        try
        {
            resultSet = dbConnection.createStatement().executeQuery(queryString);
            if (resultSet.next())
            {
                return new BankAccountDTO(accountNumber, resultSet.getDouble("balance"), resultSet.getLong("open_time_stamp"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
