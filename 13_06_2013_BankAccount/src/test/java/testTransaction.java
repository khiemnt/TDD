import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import dao.BankAccountDao;
import dao.TransactionDao;
import dto.BankAccountDTO;
import dto.TransactionDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import service.BankAccount;
import service.Transaction;
import service.Transaction.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/13/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class testTransaction {
    TransactionDao mockTransactionDao = mock(TransactionDao.class);
    BankAccountDao mockBankAccountDao = mock(BankAccountDao.class);
    Calendar mockCalendar=mock(Calendar.class);
    private String accountNumber = "1234567890";

    @Before
    public void setUp() {
        reset(mockTransactionDao);
        reset(mockBankAccountDao);
        BankAccount.setBankAccountDao(mockBankAccountDao);
        Transaction.setTransactionDao(mockTransactionDao);
        TransactionDTO.setCalendar(mockCalendar);
    }

    @Test
    public void testDepositChangeBalance() {
        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(accountDTO);
        BankAccount.deposit(accountNumber, 1000.0, "deposit");

        verify(mockBankAccountDao, times(2)).save(argument.capture());
        verify(mockBankAccountDao, times(1)).getAccount(accountNumber);

        BankAccountDTO accountFromDB = BankAccount.getAccount(accountNumber);
        assertEquals(accountNumber, argument.getValue().getAccountNumber());
        assertEquals(accountFromDB.getBalance(), argument.getValue().getBalance(), 0.001);

    }

    @Test
    public void testWithDrawChangeBalance() {
        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);

        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(accountDTO);
        BankAccount.deposit(accountNumber, 1000.0, "deposit");
        BankAccount.withdraw(accountNumber, 500.0, "withdraw");

        verify(mockBankAccountDao, times(3)).save(argument.capture());
        BankAccountDTO accountFromDB = BankAccount.getAccount(accountNumber);

        assertEquals(accountNumber, argument.getValue().getAccountNumber());
        assertEquals(500, accountFromDB.getBalance(), 0.001);
    }

    @Test
    public void testCreateTransactionPersistToDB() {
        ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor.forClass(TransactionDTO.class);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L);
        Transaction.createTransaction(accountNumber, 1000.0, "deposit");

        verify(mockTransactionDao).save(argumentTransaction.capture());
        assertEquals(accountNumber, argumentTransaction.getValue().getAccountNumber());
        assertEquals(1000, argumentTransaction.getValue().getAmount(), 0.001);
        assertFalse(argumentTransaction.getValue().isExecute());
        assertTrue(argumentTransaction.getValue().getTimeStamp() != 0);
    }

    @Test
      public void testDepositTransactionAndPersistToDB() {
        ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor.forClass(TransactionDTO.class);
        ArgumentCaptor<BankAccountDTO> argumentAccount= ArgumentCaptor.forClass(BankAccountDTO.class);


        BankAccountDTO account = BankAccount.openAccount(accountNumber);
        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L,2000L);
        BankAccount.deposit(accountNumber, 1000, "deposit 1 ");
        BankAccount.deposit(accountNumber, 1500, "deposit 2");

        verify(mockTransactionDao,times(2)).save(argumentTransaction.capture());
        List<TransactionDTO> transactionDTOList=argumentTransaction.getAllValues() ;

        assertEquals(accountNumber,transactionDTOList.get(0).getAccountNumber());
        assertEquals(1000,transactionDTOList.get(0).getAmount(),0.001);
        assertEquals(1000,transactionDTOList.get(0).getTimeStamp(),0.001);

        assertEquals(accountNumber,transactionDTOList.get(1).getAccountNumber());
        assertEquals(1500,transactionDTOList.get(1).getAmount(),0.001);
        assertEquals(2000,transactionDTOList.get(1).getTimeStamp(),0.001);

        verify(mockBankAccountDao,times(3)).save(argumentAccount.capture());
        assertEquals(2500,BankAccount.getAccount(accountNumber).getBalance(),0.001);
    }

    @Test
    public void testWithdrawTransactionAndPersistToDB(){
        ArgumentCaptor<TransactionDTO> argumentTransaction=ArgumentCaptor.forClass(TransactionDTO.class);
        ArgumentCaptor<BankAccountDTO> argumentAccount=ArgumentCaptor.forClass(BankAccountDTO.class);

        BankAccountDTO account=BankAccount.openAccount(accountNumber);
        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L,2000L,3000L);

        BankAccount.deposit(accountNumber, 2000, "deposit ");
        BankAccount.withdraw(accountNumber, 1000, "withdraw ");
        BankAccount.withdraw(accountNumber, 500, "withdraw ");

        verify(mockTransactionDao,times(3)).save(argumentTransaction.capture());
        List<TransactionDTO> transactionDTOList=argumentTransaction.getAllValues();

        assertEquals(accountNumber,transactionDTOList.get(1).getAccountNumber());
        assertEquals(1000,transactionDTOList.get(1).getAmount(),0.001);
        assertEquals(2000,transactionDTOList.get(1).getTimeStamp(),0.001);

        assertEquals(accountNumber,transactionDTOList.get(2).getAccountNumber());
        assertEquals(500,transactionDTOList.get(2).getAmount(),0.001);
        assertEquals(3000,transactionDTOList.get(2).getTimeStamp(),0.001);

        verify(mockBankAccountDao,times(4)).save(argumentAccount.capture());
        assertEquals(500,argumentAccount.getValue().getBalance(),0.001);
    }

    @Test
    public void testGetTransactionsOccurredByAccountNumber(){

        ArgumentCaptor<String> argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        BankAccountDTO account=BankAccount.openAccount(accountNumber);

        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L,2000L,3000L);

        BankAccount.deposit(accountNumber, 2000, "deposit ");
        BankAccount.withdraw(accountNumber, 1000, "withdraw ");
        BankAccount.deposit(accountNumber, 1500, "deposit ");

        TransactionDTO transactionDTO1=new TransactionDTO(accountNumber,2000,"deposit");
        TransactionDTO transactionDTO2=new TransactionDTO(accountNumber,1000,"deposit");
        TransactionDTO transactionDTO3=new TransactionDTO(accountNumber,1500,"deposit");
        List<TransactionDTO> listFromDB= new ArrayList<TransactionDTO>();
        listFromDB.add(transactionDTO1)  ;
        listFromDB.add(transactionDTO2)  ;
        listFromDB.add(transactionDTO3)  ;

        when(mockTransactionDao.getTransactionsOccurred(accountNumber)).thenReturn(listFromDB);
        List<TransactionDTO> transactionDTOList=Transaction.getTransactionsOccurred(accountNumber);

        assertEquals(3,transactionDTOList.size());
        verify(mockTransactionDao).getTransactionsOccurred(argumentAccountNumber.capture());
        assertEquals(accountNumber,argumentAccountNumber.getValue());

    }
    @Test
    public void testGetTransactionsOccurredInPeriod(){
        ArgumentCaptor<String> argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> argumentStartTime=ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> argumentStopTime=ArgumentCaptor.forClass(Long.class);

        BankAccountDTO account=BankAccount.openAccount(accountNumber);
        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L,2000L,3000L);

        BankAccount.deposit(accountNumber, 2000, "deposit ");
        BankAccount.withdraw(accountNumber, 1000, "withdraw ");
        BankAccount.deposit(accountNumber, 1500, "deposit ");


        TransactionDTO transactionDTO1=new TransactionDTO(accountNumber,1000,"deposit");
        TransactionDTO transactionDTO2=new TransactionDTO(accountNumber,1500,"deposit");
        List<TransactionDTO> listFromDB= new ArrayList<TransactionDTO>();
        listFromDB.add(transactionDTO1)  ;
        listFromDB.add(transactionDTO2)  ;

        long startTime=1500,stopTime=4000;
        when(mockTransactionDao.getTransactionsOccurred(accountNumber,startTime,stopTime)).thenReturn(listFromDB);
        List<TransactionDTO> transactionDTOList=Transaction.getTransactionsOccurred(accountNumber,startTime,stopTime);
        assertEquals(2,transactionDTOList.size());

        verify(mockTransactionDao).getTransactionsOccurred(argumentAccountNumber.capture(),argumentStartTime.capture(),argumentStopTime.capture());
        assertEquals(accountNumber,argumentAccountNumber.getValue());
        assertEquals(startTime,argumentStartTime.getValue(),0.001);
        assertEquals(stopTime,argumentStopTime.getValue(),0.001);

    }

    @Test
    public void getNTransactionToCurrentTime(){
        ArgumentCaptor<String> argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> argumentNumOfTransaction=ArgumentCaptor.forClass(Integer.class);

        BankAccountDTO account=BankAccount.openAccount(accountNumber);
        when(mockBankAccountDao.getAccount(accountNumber)).thenReturn(account);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L,2000L,3000L);

        BankAccount.deposit(accountNumber, 2000, "deposit ");
        BankAccount.withdraw(accountNumber, 1000, "withdraw ");
        BankAccount.deposit(accountNumber, 1500, "deposit ");

        TransactionDTO transactionDTO1=new TransactionDTO(accountNumber,1500,"deposit");
        List<TransactionDTO> listFromDB= new ArrayList<TransactionDTO>();
        listFromDB.add(transactionDTO1)  ;
        int numOfTransaction=1;
        when(mockTransactionDao.getTransactionsOccurred(accountNumber,numOfTransaction)).thenReturn(listFromDB);
        List<TransactionDTO> transactionDTOList=Transaction.getTransactionsOccurred(accountNumber,numOfTransaction);
        assertEquals(1,transactionDTOList.size());

        verify(mockTransactionDao).getTransactionsOccurred(argumentAccountNumber.capture(),argumentNumOfTransaction.capture());
        assertEquals(numOfTransaction,argumentNumOfTransaction.getValue().intValue());

    }
}
