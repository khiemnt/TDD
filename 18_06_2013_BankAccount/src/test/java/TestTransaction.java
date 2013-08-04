import dao.BankAccountDao;
import dao.TransactionDao;
import dto.BankAccountDTO;
import dto.TransactionDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import service.BankAccount;
import service.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/19/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestTransaction {
    private BankAccountDao mockAccountDao=mock(BankAccountDao.class);
    private TransactionDao mockTransactionDao=mock(TransactionDao.class);
    Calendar mockCalendar=mock(Calendar.class);
    private String accountNumber="1234567890";

    @Before
    public void setUp(){
        reset(mockTransactionDao);
        reset(mockAccountDao);
        Transaction.setTransactionDao(mockTransactionDao);
        BankAccount.setBankAccountDao(mockAccountDao);
        TransactionDTO.setCalendar(mockCalendar);
    }
    @Test
    public void testDeposit() throws Exception
    {
      ArgumentCaptor<BankAccountDTO> argumentAccount= ArgumentCaptor.forClass(BankAccountDTO.class);
      BankAccountDTO account= BankAccount.openAccount(accountNumber);
      when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
      BankAccount.deposit(accountNumber,1000,"deposit");
      verify(mockAccountDao,times(2)).save(argumentAccount.capture());
      assertEquals(accountNumber, argumentAccount.getValue().getAccountNumber());
      assertEquals(1000, argumentAccount.getValue().getBalance(),0.001);

    }
    @Test
    public void testWithdrawal() throws Exception
    {
        ArgumentCaptor<BankAccountDTO> argumentAccount= ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccountDTO account= BankAccount.openAccount(accountNumber);

        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        BankAccount.deposit(accountNumber,1000,"deposit");
        BankAccount.withdraw(accountNumber, 600, "withdraw");

        verify(mockAccountDao,times(3)).save(argumentAccount.capture());
        assertEquals(accountNumber, argumentAccount.getValue().getAccountNumber());
        assertEquals(400, argumentAccount.getValue().getBalance(),0.001);
    }
    @Test
    public void testCreateTransactionAndSaveToDB() throws Exception
    {
        ArgumentCaptor<TransactionDTO> argumentTransaction= ArgumentCaptor.forClass(TransactionDTO.class);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L);
        Transaction.createTransaction(accountNumber, 1000, "deposit");
        verify(mockTransactionDao).save(argumentTransaction.capture());

        assertEquals(accountNumber,argumentTransaction.getValue().getAccountNumber());
        assertEquals(1000,argumentTransaction.getValue().getAmount(),0.001);
        assertEquals(1000,argumentTransaction.getValue().getTimeStamp(),0.001);
    }
    @Test
    public void testDepositAndSaveTransactionToDB() throws Exception
    {
        ArgumentCaptor<TransactionDTO> argumentTransaction= ArgumentCaptor.forClass(TransactionDTO.class);
        ArgumentCaptor<BankAccountDTO> argumentAccount= ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccountDTO account= BankAccount.openAccount(accountNumber);

        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L,2000L);
        BankAccount.deposit(accountNumber,1000,"deposit");
        BankAccount.deposit(accountNumber, 500, "deposit");
        verify(mockTransactionDao,times(2)).save(argumentTransaction.capture());
        List<TransactionDTO> list=argumentTransaction.getAllValues();

        assertEquals(accountNumber,list.get(0).getAccountNumber());
        assertEquals(1000,list.get(0).getAmount(),0.001);
        assertEquals(1000,list.get(0).getTimeStamp());

        assertEquals(accountNumber,list.get(1).getAccountNumber());
        assertEquals(500,list.get(1).getAmount(),0.001);
        assertEquals(2000,list.get(1).getTimeStamp());

        verify(mockAccountDao,times(3)).save(argumentAccount.capture());
        assertEquals(1500,account.getBalance(),0.001);

    }
    @Test
    public void testWithdrawAndSaveTransactionToDB() throws Exception{
        ArgumentCaptor<TransactionDTO> argumentTransaction= ArgumentCaptor.forClass(TransactionDTO.class);
        ArgumentCaptor<BankAccountDTO> argumentAccount= ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccountDTO account= BankAccount.openAccount(accountNumber);

        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L,2000L,3000L);

        BankAccount.deposit(accountNumber,2000,"deposit");
        BankAccount.withdraw(accountNumber,1000,"withdraw");
        BankAccount.withdraw(accountNumber, 500, "withdraw");
        verify(mockTransactionDao,times(3)).save(argumentTransaction.capture());
        List<TransactionDTO> list=argumentTransaction.getAllValues();

        assertEquals(accountNumber,list.get(1).getAccountNumber());
        assertEquals(1000,list.get(1).getAmount(),0.001);
        assertEquals(2000,list.get(1).getTimeStamp(),0.001);

        assertEquals(accountNumber,list.get(2).getAccountNumber());
        assertEquals(500,list.get(2).getAmount(),0.001);
        assertEquals(3000,list.get(2).getTimeStamp(),0.001);

        verify(mockAccountDao,times(4)).save(argumentAccount.capture());
        assertEquals(500,account.getBalance(),0.001);
    }

    @Test
    public void testWithdrawOverBalance() throws Exception{
        BankAccountDTO account= BankAccount.openAccount(accountNumber);
        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        BankAccount.deposit(accountNumber,500,"deposit");
        try{
            BankAccount.withdraw(accountNumber,1000,"deposit");
            fail();
        }catch(RuntimeException ex){
            assertEquals("Withdraw fail,Try again!",ex.getMessage());
        }

        assertEquals(500,account.getBalance(),0.001);

    }
    @Test
     public void testGetTransactionsOccurredByAccountNumber() throws Exception{
        ArgumentCaptor<String> argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        BankAccountDTO account=BankAccount.openAccount(accountNumber);
        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        BankAccount.deposit(accountNumber,2000,"deposit");
        BankAccount.withdraw(accountNumber,1000,"withdraw");
        BankAccount.deposit(accountNumber, 500, "deposit");

        BankAccount.getTransactionsOccurred(accountNumber);
        verify(mockTransactionDao).getTransactionsOccurred(argumentAccountNumber.capture());
        assertEquals(accountNumber,argumentAccountNumber.getValue());
    }
    @Test
    public void testGetTransactionsOccurredInPeriod() throws Exception{
        ArgumentCaptor<String> argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> argumentStartTime=ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> argumentStopTime=ArgumentCaptor.forClass(Long.class);
        BankAccountDTO account=BankAccount.openAccount(accountNumber);
        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000l,2000l,3000l)  ;
        BankAccount.deposit(accountNumber,2000,"deposit");
        BankAccount.withdraw(accountNumber,1000,"withdraw");
        BankAccount.deposit(accountNumber, 500, "deposit");

        long startTime=1500L,stopTime=3500L;
        BankAccount.getTransactionsOccurred(accountNumber, startTime, stopTime) ;
        verify(mockTransactionDao).getTransactionsOccurred(argumentAccountNumber.capture(), argumentStartTime.capture(), argumentStopTime.capture());
        assertEquals(accountNumber, argumentAccountNumber.getValue());
        assertEquals(startTime, argumentStartTime.getValue().longValue());
        assertEquals(stopTime,argumentStopTime.getValue().longValue());
    }
    @Test
    public void testGetNTransactionToCurrentTime()throws Exception{
        ArgumentCaptor<String> argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> argumentNumOfTransaction=ArgumentCaptor.forClass(Integer.class);
        BankAccountDTO account=BankAccount.openAccount(accountNumber);
        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000l,2000l)  ;
        BankAccount.deposit(accountNumber,2000,"deposit");
        BankAccount.withdraw(accountNumber,1000,"withdraw");
        int numOfTransaction=1;
        Transaction.getTransactionsOccurred(accountNumber,numOfTransaction) ;
        verify(mockTransactionDao).getTransactionsOccurred(argumentAccountNumber.capture(),argumentNumOfTransaction.capture());
        assertEquals(accountNumber,argumentAccountNumber.getValue());
        assertEquals(numOfTransaction,argumentNumOfTransaction.getValue().intValue());
    }
}
