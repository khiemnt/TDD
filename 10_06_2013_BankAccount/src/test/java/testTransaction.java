import dao.BankAccountDao;
import dao.TransactionDao;
import dto.BankAccountDTO;
import dto.TransactionDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import service.BankAccount;
import service.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/10/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class testTransaction {
    private TransactionDao mockTransactionDao = mock(TransactionDao.class);
    private BankAccountDao mockAccountDao = mock(BankAccountDao.class);
    public String accountNumber = "1234567";

    @Before
    public void setUp() {
        reset(mockTransactionDao);
        reset(mockAccountDao);
        Transaction.setTransactionDao(mockTransactionDao);
        BankAccount.setBankAccountDao(mockAccountDao);
    }

    @Test
    public void testCreateTransactionPersistenceToDB() {

        Transaction.createTransaction(accountNumber, 1000.0, "deposit");
        ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
        verify(mockTransactionDao).save(argument.capture());
        assertEquals(accountNumber, argument.getValue().getAccountNumber());
        assertEquals(1000, argument.getValue().getAmount(), 0.001);
        assertFalse(argument.getValue().isExecuted());
        assertTrue(argument.getValue().getTimestamp() != 0);
    }

    @Test
    public void testDeposit() {

        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccountDTO account = new BankAccountDTO(accountNumber, 0);

        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        BankAccount.deposit(accountNumber, 1000, "Deposit");
        verify(mockAccountDao, times(1)).save(argument.capture());
        verify(mockAccountDao, times(1)).getAccount(accountNumber);

        assertEquals(1000, argument.getValue().getBalance(), 0.001);
        assertEquals(accountNumber, argument.getValue().getAccountNumber());

    }

    @Test
    public void testDepositPersisToDB() {

        ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor.forClass(TransactionDTO.class);
        ArgumentCaptor<BankAccountDTO> argumentAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
        Calendar calendar = mock(Calendar.class);
        TransactionDTO.setCalendar(calendar);
        when(calendar.getTimeInMillis()).thenReturn(1000L).thenReturn(2000L);

        BankAccountDTO account = new BankAccountDTO(accountNumber, 0);
        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        BankAccount.deposit(accountNumber, 1000, "Deposit 1");
        BankAccount.deposit(accountNumber, 2000, "Deposit 2");

        verify(mockTransactionDao, times(2)).save(argumentTransaction.capture());


        List<TransactionDTO> transactionDTOList = argumentTransaction.getAllValues();

        assertEquals(accountNumber, transactionDTOList.get(0).getAccountNumber());
        assertEquals(1000, transactionDTOList.get(0).getAmount(), 0.001);
        assertEquals(1000, transactionDTOList.get(0).getTimestamp());

        assertEquals(accountNumber, transactionDTOList.get(1).getAccountNumber());
        assertEquals(2000, transactionDTOList.get(1).getAmount(), 0.001);
        assertEquals(2000, transactionDTOList.get(1).getTimestamp());

        verify(mockAccountDao, times(2)).save(argumentAccount.capture());
        assertEquals(3000, argumentAccount.getValue().getBalance(), 0.001);

    }

    @Test
    public void testWithdraw() {

        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccountDTO accountFromDB = new BankAccountDTO(accountNumber, 1000);

        when(mockAccountDao.getAccount(accountNumber)).thenReturn(accountFromDB);
        BankAccount.withdraw(accountNumber, 500, "withdraw");

        verify(mockAccountDao, times(1)).save(argument.capture());
        assertEquals(500, argument.getValue().getBalance(), 0.001);
        assertEquals(accountNumber, argument.getValue().getAccountNumber());

    }

    @Test
    public void testWithdrawPersisToDB() {
        ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor.forClass(TransactionDTO.class);
        ArgumentCaptor<BankAccountDTO> argumentAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
        Calendar calendar = mock(Calendar.class);
        TransactionDTO.setCalendar(calendar);
        when(calendar.getTimeInMillis()).thenReturn(1000L).thenReturn(2000L);

        BankAccountDTO account = new BankAccountDTO(accountNumber, 2000);
        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        BankAccount.withdraw(accountNumber, 500, "Deposit 1");
        BankAccount.withdraw(accountNumber, 1000, "Deposit 2");

        verify(mockTransactionDao, times(2)).save(argumentTransaction.capture());

        List<TransactionDTO> transactionDTOList = argumentTransaction.getAllValues();

        assertEquals(accountNumber, transactionDTOList.get(0).getAccountNumber());
        assertEquals(500, transactionDTOList.get(0).getAmount(), 0.001);
        assertEquals(1000, transactionDTOList.get(0).getTimestamp());

        assertEquals(accountNumber, transactionDTOList.get(1).getAccountNumber());
        assertEquals(1000, transactionDTOList.get(1).getAmount(), 0.001);
        assertEquals(2000, transactionDTOList.get(1).getTimestamp());

        verify(mockAccountDao, times(2)).save(argumentAccount.capture());
        assertEquals(500, argumentAccount.getValue().getBalance(), 0.001);

        try {
            BankAccount.withdraw(accountNumber, 1000, "Deposit 3");
            fail();
        } catch (NumberFormatException ex) {
            assertEquals("Withdraw money greater principal!", ex.getMessage());
        }
    }

    @Test
    public void testGetTransactionsOccurredByAccountNumber() {
        ArgumentCaptor<String>argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        List<TransactionDTO> transactionDTOList = new ArrayList<TransactionDTO>();
        TransactionDTO transactionDTO1 = Transaction.createTransaction(accountNumber, 1000.0, "deposit 1");
        TransactionDTO transactionDTO2 = Transaction.createTransaction(accountNumber, 2000.0, "withdraw 2");
        TransactionDTO transactionDTO3 = Transaction.createTransaction(accountNumber, 3000.0, "deposit 2");

        transactionDTOList.add(transactionDTO1);
        transactionDTOList.add(transactionDTO2);
        transactionDTOList.add(transactionDTO3);

        when(mockTransactionDao.getTransactionsOccurred(accountNumber)).thenReturn(transactionDTOList);
        List<TransactionDTO>transactionListFromDB= BankAccount.getTransactionsOccurred(accountNumber);
        assertEquals(3, transactionListFromDB.size());

        verify(mockTransactionDao).getTransactionsOccurred(argumentAccountNumber.capture());
        assertEquals(accountNumber,argumentAccountNumber.getValue());
    }
    @Test
    public void testGetTransactionsOccurredInPeriod(){
        Calendar calendar = mock(Calendar.class);
        TransactionDTO.setCalendar(calendar);
        ArgumentCaptor<String> argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> argumentStartTime=ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> argumentStopTime=ArgumentCaptor.forClass(Long.class);

        List<TransactionDTO> transactionDTOListResult = new ArrayList<TransactionDTO>();
        when(calendar.getTimeInMillis()).thenReturn(1000L).thenReturn(2000L).thenReturn(3000L);
        TransactionDTO transactionDTO1 = Transaction.createTransaction(accountNumber, 1000.0, "deposit 1");
        TransactionDTO transactionDTO2 = Transaction.createTransaction(accountNumber, 2000.0, "withdraw 2");
        TransactionDTO transactionDTO3 = Transaction.createTransaction(accountNumber, 3000.0, "deposit 2");

        transactionDTOListResult.add(transactionDTO2)  ;
        transactionDTOListResult.add(transactionDTO3)  ;
        long startTime=1500;
        long stopTime=3100;

        when(mockTransactionDao.getTransactionsOccurred(accountNumber, startTime, stopTime)).thenReturn(transactionDTOListResult);
        List<TransactionDTO>transactionListFromDB= BankAccount.getTransactionsOccurred(accountNumber, startTime, stopTime) ;
        //test with argument  and list return from DB
        verify(mockTransactionDao,times(1)).getTransactionsOccurred(argumentAccountNumber.capture(),argumentStartTime.capture(),argumentStopTime.capture());

        assertEquals(2, transactionListFromDB.size());
        assertEquals(accountNumber,argumentAccountNumber.getValue());
        assertEquals(startTime,argumentStartTime.getValue(),0.001);
        assertEquals(stopTime,argumentStopTime.getValue(),0.001);
    }
    @Test
    public void testGetNewTransactionToCurrentTime(){

        ArgumentCaptor<String>argumentAccountNumber=ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer>argumentNumber=ArgumentCaptor.forClass(Integer.class);


        Calendar calendar = mock(Calendar.class);
        TransactionDTO.setCalendar(calendar);
        List<TransactionDTO> transactionDTOListResult = new ArrayList<TransactionDTO>();
        when(calendar.getTimeInMillis()).thenReturn(1000L).thenReturn(2000L).thenReturn(3000L);
        TransactionDTO transactionDTO1 = Transaction.createTransaction(accountNumber, 1000.0, "deposit 1");
        TransactionDTO transactionDTO2 = Transaction.createTransaction(accountNumber, 2000.0, "withdraw 2");
        TransactionDTO transactionDTO3 = Transaction.createTransaction(accountNumber, 3000.0, "deposit 2");

        transactionDTOListResult.add(transactionDTO3) ;
        int numOfTransaction=1;
        when(mockTransactionDao.getTransactionsOccurredNewTimes(accountNumber, numOfTransaction)).thenReturn(transactionDTOListResult);

        List<TransactionDTO>transactionListFromDB=Transaction.GetNewTransactionToCurrentTime(accountNumber,numOfTransaction);


        assertEquals(1,transactionListFromDB.size());
        assertEquals(3000,transactionListFromDB.get(0).getAmount(),0.001);
        assertEquals(3000,transactionListFromDB.get(0).getTimestamp());

        verify(mockTransactionDao).getTransactionsOccurredNewTimes(argumentAccountNumber.capture(),argumentNumber.capture());
        assertEquals(accountNumber,argumentAccountNumber.getValue());
        assertEquals(numOfTransaction,argumentNumber.getValue().intValue());

    }
}
