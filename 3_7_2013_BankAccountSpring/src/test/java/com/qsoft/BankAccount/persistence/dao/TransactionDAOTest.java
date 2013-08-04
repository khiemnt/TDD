package com.qsoft.BankAccount.persistence.dao;

import com.qsoft.BankAccount.business.BankAccountService;
import com.qsoft.BankAccount.persistence.model.BankAccountEntity;
import com.qsoft.BankAccount.persistence.model.TransactionEntity;
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

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

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
public class TransactionDAOTest
{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionDAO transactionDAO;


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
    public void testCreateTransactionAndSaveToDB() throws Exception
    {

        TransactionEntity transactionDTO=new TransactionEntity("1122345678",1000.0,123455L,"deposit");
        transactionDAO.save(transactionDTO);
        List<TransactionEntity> transactionDTOFromDB=transactionDAO.getTransactionsOccurred("1122345678");
        assertEquals("1122345678",transactionDTOFromDB.get(0).getAccountNumber());
        assertEquals(123455L,transactionDTOFromDB.get(0).getTimeStamp(),0.001);
        assertEquals(1000,transactionDTOFromDB.get(0).getAmount(),0.001);
        assertEquals("deposit",transactionDTOFromDB.get(0).getDescription());
    }
    @Test
    public void testGetListTransactionsOccurredByAccountNumber(){
       List<TransactionEntity> transactionEntityList=transactionDAO.getTransactionsOccurred(accountNumber);
        assertEquals("1234567890",transactionEntityList.get(0).getAccountNumber());
        assertEquals(1500,transactionEntityList.get(0).getTimeStamp(),0.001);
        assertEquals(1000,transactionEntityList.get(0).getAmount(),0.001);
        assertEquals("deposit",transactionEntityList.get(0).getDescription());

        assertEquals("1234567890",transactionEntityList.get(1).getAccountNumber());
        assertEquals(3000,transactionEntityList.get(1).getTimeStamp(),0.001);
        assertEquals(2000,transactionEntityList.get(1).getAmount(),0.001);
        assertEquals("withdraw",transactionEntityList.get(1).getDescription());
    }

    @Test
    public void getTransactionsOccurredInPeriod(){
        long startTime=1000L,stopTime=2500L;
        List<TransactionEntity> transactionDTOList=transactionDAO.getTransactionsOccurred("1234567890", startTime, stopTime) ;
        assertEquals("1234567890",transactionDTOList.get(0).getAccountNumber());
        assertEquals(1500,transactionDTOList.get(0).getTimeStamp(),0.001);
        assertEquals(1000,transactionDTOList.get(0).getAmount(),0.001);
        assertEquals("deposit",transactionDTOList.get(0).getDescription());
    }

    @Test
    public void testGetNTransactionToCurrentTime()throws Exception {
        int n=1;
        List<TransactionEntity> transactionDTOList=transactionDAO.getTransactionsOccurred("1234567890", n) ;
        assertEquals("1234567890",transactionDTOList.get(0).getAccountNumber());
        assertEquals(3000,transactionDTOList.get(0).getTimeStamp(),0.001);
        assertEquals(2000,transactionDTOList.get(0).getAmount(),0.001);
        assertEquals("withdraw",transactionDTOList.get(0).getDescription());

    }

}
