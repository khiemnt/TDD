package com.qsoft.BankAccount.persistence.dao;

import com.qsoft.BankAccount.business.BankAccountService;
import com.qsoft.BankAccount.persistence.model.BankAccountEntity;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 11:14 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContext.xml"})
@TransactionConfiguration(defaultRollback = true)
// Importance, as the transaction will be rollback for each test
// give us a clean state.
@Transactional
public class BankAccountDAOTest
{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BankAccountDAO bankAccountDAO;


    @Autowired
    private BankAccountService bankAccountService;

    final static String accountNumber = "1234567890";

    @Autowired
    private DataSource dataSourceTest;

    private IDatabaseTester databaseTester;

    @Before
    public void setup() throws Exception
    {
        IDataSet dataSet = readDataSet();  // read data from xml file
        cleanlyInsert(dataSet);  // empty the db and insert data
    }

    private IDataSet readDataSet() throws Exception
    {
        return new FlatXmlDataSetBuilder().build(System.class.getResource("/dataset.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception
    {
        databaseTester = new DataSourceDatabaseTester(dataSourceTest);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @After
    public void tearDown() throws Exception
    {
        databaseTester.onTearDown();
    }

    @Test
    public void testGetAccount() throws Exception
    {
        BankAccountEntity account = bankAccountDAO.getAccount(accountNumber);
        assertEquals("1234567890", account.getAccountNumber());
        assertEquals(100, account.getBalance(), 0.01);
        assertEquals(123456, account.getTimeStamp());
    }
     @Test
    public void testGetAccountThatNotExist(){
         BankAccountEntity account=bankAccountDAO.getAccount("1223456789");
         assertNull(account);
     }
     @Test
    public void testOpenNewAccountAndPersistToDB() throws Exception
     {
           BankAccountEntity newAccount=new BankAccountEntity("1223456789",2000,1234567) ;
           bankAccountDAO.save(newAccount);
           BankAccountEntity accountFromDB=bankAccountDAO.getAccount("1223456789") ;
           assertEquals("1223456789",accountFromDB.getAccountNumber());
           assertEquals(2000,accountFromDB.getBalance(),0.001);
           assertEquals(1234567,accountFromDB.getTimeStamp(),0.001);
     }
    @Test
    public void testChangeBalanceAndSaveToDB() throws  Exception{
        BankAccountEntity account=bankAccountDAO.getAccount(accountNumber);
        account.setBalance(account.getBalance()+1000);

        entityManager.detach(account);
        bankAccountDAO.save(account);
        BankAccountEntity accountFromDB=bankAccountDAO.getAccount(accountNumber) ;
        assertEquals(accountNumber,accountFromDB.getAccountNumber());
        assertEquals(1100,accountFromDB.getBalance(),0.001);
        assertEquals(123456,accountFromDB.getTimeStamp(),0.001);
    }
    @Test
    public void testWithNegativeBalance() throws  Exception {
        try{
            BankAccountEntity account=new BankAccountEntity("1122345678",-1000,100234) ;
            bankAccountDAO.save(account);
            fail();
        }catch (RuntimeException ex){
            assertEquals("Balance must greater than 0",ex.getMessage());
        }
    }
}
