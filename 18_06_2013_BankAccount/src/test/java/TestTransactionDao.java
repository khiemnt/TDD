import dao.BankAccountDao;
import dao.TransactionDao;
import dto.BankAccountDTO;
import dto.TransactionDTO;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.BankAccount;
import service.Transaction;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/25/13
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestTransactionDao
{
    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private String accountNumber="1112345678";


    @BeforeClass
    public static void createSchema() throws Exception {
        String schemaFileName = System.class.getResource("/schema").toString().substring(6);
        RunScript.execute(JDBC_URL, USER, PASSWORD, schemaFileName, Charset.forName("UTF8"), false);
    }

    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        cleanlyInsert(dataSet);
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(System.class.getResource("/data"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }
    private DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
     @Test
    public void testCreateTransactionAndSaveToDB() throws Exception
     {

         TransactionDao transactionDao = new TransactionDao(dataSource());
         TransactionDTO transactionDTO=new TransactionDTO(accountNumber,123455L,1000.0,"deposit");
         transactionDao.save(transactionDTO);
         List<TransactionDTO> transactionDTOFromDB=transactionDao.getTransactionsOccurred(accountNumber);
         assertEquals(accountNumber,transactionDTOFromDB.get(0).getAccountNumber());
         assertEquals(123455L,transactionDTOFromDB.get(0).getTimeStamp(),0.001);
         assertEquals(1000,transactionDTOFromDB.get(0).getAmount(),0.001);
         assertEquals("deposit",transactionDTOFromDB.get(0).getDescription());
     }
     @Test
    public void testGetTransactionsOccurredByAccountNumber() throws Exception
     {
         TransactionDao transactionDao=new TransactionDao(dataSource());
         List<TransactionDTO> transactionDTOList=transactionDao.getTransactionsOccurred("1234567890");
         assertEquals("1234567890",transactionDTOList.get(0).getAccountNumber());
         assertEquals(1500,transactionDTOList.get(0).getTimeStamp(),0.001);
         assertEquals(1000,transactionDTOList.get(0).getAmount(),0.001);
         assertEquals("deposit",transactionDTOList.get(0).getDescription());

         assertEquals("1234567890",transactionDTOList.get(1).getAccountNumber());
         assertEquals(3000,transactionDTOList.get(1).getTimeStamp(),0.001);
         assertEquals(2000,transactionDTOList.get(1).getAmount(),0.001);
         assertEquals("withdraw",transactionDTOList.get(1).getDescription());
     }
    @Test
    public void testGetTransactionsOccurredInPeriod() throws Exception
    {
        TransactionDao transactionDao=new TransactionDao(dataSource());
        long startTime=1000L,stopTime=2500L;
        List<TransactionDTO> transactionDTOList=transactionDao.getTransactionsOccurred("1234567890", startTime, stopTime) ;
        assertEquals("1234567890",transactionDTOList.get(0).getAccountNumber());
        assertEquals(1500,transactionDTOList.get(0).getTimeStamp(),0.001);
        assertEquals(1000,transactionDTOList.get(0).getAmount(),0.001);
        assertEquals("deposit",transactionDTOList.get(0).getDescription());
    }
    @Test
    public void testGetNTransactionToCurrentTime()throws Exception {
        TransactionDao transactionDao=new TransactionDao(dataSource());
        int numOfTransaction=1;
        List<TransactionDTO> transactionDTOList=transactionDao.getTransactionsOccurred("1234567890", numOfTransaction) ;
        assertEquals("1234567890",transactionDTOList.get(0).getAccountNumber());
        assertEquals(3000,transactionDTOList.get(0).getTimeStamp(),0.001);
        assertEquals(2000,transactionDTOList.get(0).getAmount(),0.001);
        assertEquals("withdraw",transactionDTOList.get(0).getDescription());

    }
}
