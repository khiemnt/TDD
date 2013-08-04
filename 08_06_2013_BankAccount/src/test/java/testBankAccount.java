import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.*;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/8/13
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class testBankAccount {
    private BankAccountDao mockAccountDao = mock(BankAccountDao.class);

    @Before
    public void setUp() {
        reset(mockAccountDao);
        BankAccount.setBankAccountDao(mockAccountDao);
    }

    @Test
    public void testNewAccountAndPersistenceToDB() {
        BankAccount.openAccount("1234567");
        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockAccountDao).save(argument.capture());
        assertEquals("1234567", argument.getValue().getAccountNumber());
        assertEquals(0, argument.getValue().getBalance(), 0.001);
    }

    @Test
    public void testGetAccountIsPersistenceFromDB() {

        BankAccountDTO account = BankAccount.openAccount("1234567");
        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockAccountDao,times(1)).save(argument.capture());

        when(mockAccountDao.getAccount(account.getAccountNumber())).thenReturn(account);
        BankAccountDTO accountFromDB = BankAccount.getAccount(account.getAccountNumber());
        assertEquals("1234567", accountFromDB.getAccountNumber());
        assertEquals(0, accountFromDB.getBalance(),0.001);
    }

    @Test
    public void testDepositTransactionAndPersistenceToDB() {
        BankAccountDTO account = BankAccount.openAccount("1234567");
        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccount.deposit(account, 1000);
        verify(mockAccountDao, times(2)).save(argument.capture());
        assertEquals(1000, argument.getValue().getBalance(), 0.001);
    }

    @Test
    public void testWithDrawTransactionAndPersistenceToDB() {
        BankAccountDTO account = BankAccount.openAccount("1234567");
        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccount.deposit(account, 1000);
        verify(mockAccountDao, times(2)).save(argument.capture());
        BankAccount.withdraw(account, 500);
        verify(mockAccountDao, times(3)).save(argument.getValue());
        assertEquals(500, argument.getValue().getBalance(), 0.001);
    }
}
