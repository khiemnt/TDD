import dao.BankAccountDao;
import dto.BankAccountDTO;
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

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.sql.SQLException;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: qs095
 * Date: 6/24/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestBankAccountDao {
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
     public void testOpenNewAccountPersistToDB() throws Exception{
        BankAccountDao bankAccountDao = new BankAccountDao(dataSource());
        BankAccountDTO accountDTO=new BankAccountDTO(accountNumber,2000.0,12321222);
        bankAccountDao.save(accountDTO);
        BankAccountDTO accountFromDB=bankAccountDao.getAccount(accountNumber);

        assertEquals(accountDTO.getAccountNumber(), accountFromDB.getAccountNumber());
        assertEquals(accountDTO.getBalance(),accountFromDB.getBalance(),0.001);
        assertEquals(accountDTO.getTimeStamp(),accountFromDB.getTimeStamp(),0.001);
    }
     @Test
     public void testGetAccountThatNotExist() throws Exception{
         BankAccountDao bankAccountDao = new BankAccountDao(dataSource());
         BankAccountDTO accountDTO = bankAccountDao.getAccount("3234567890");
         assertNull(accountDTO);
     }
    @Test
    public void testGetAccountByAccountNumber() throws Exception {
        BankAccountDao bankAccountDao = new BankAccountDao(dataSource());
        BankAccountDTO account = bankAccountDao.getAccount("1234567890");

        assertEquals("1234567890", account.getAccountNumber());
        assertEquals(100,account.getBalance(),0.001);
    }
    @Test
    public void testChangeBalanceAndSaveToDB() throws Exception
    {
        BankAccountDao bankAccountDao = new BankAccountDao(dataSource());
        BankAccountDTO account=bankAccountDao.getAccount("1234567890")  ;
        account.setBalance(2000);
        bankAccountDao.save(account);
        BankAccountDTO accountAfterDeposit=bankAccountDao.getAccount("1234567890")  ;
        assertEquals(2000,accountAfterDeposit.getBalance(),0.001);
    }
    @Test
    public void testSaveWithNegativeBalance() throws  Exception{
        try{
        BankAccountDao bankAccountDao = new BankAccountDao(dataSource());
        BankAccountDTO accountDTO=new BankAccountDTO(accountNumber,-50,224242);
        bankAccountDao.save(accountDTO);
        fail();
        }catch (RuntimeException ex){
            assertEquals("Balance must greater than 0",ex.getMessage());
        }

    }
}
