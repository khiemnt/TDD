import dao.BankAccountDao;
import dto.BankAccountDTO;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import service.BankAccount;
import sun.swing.BakedArrayList;

import java.sql.SQLException;
import java.util.Calendar;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/18/13
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestBankAccount {
    private String accountNumber = "1234567890";
    private BankAccountDao mockAccountDao = mock(BankAccountDao.class);
    Calendar mockCalendar=mock(Calendar.class);

    @Before
    public void setUp() {
        reset(mockAccountDao);
        BankAccount.setBankAccountDao(mockAccountDao);
        BankAccountDTO.setCalendar(mockCalendar);
    }

    @Test
    public void testOpenNewAccountAndPersistToDB()
    {
        ArgumentCaptor<BankAccountDTO> argumentAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
        when(mockCalendar.getTimeInMillis()).thenReturn(1000L);

        BankAccount.openAccount(accountNumber);
        verify(mockAccountDao).save(argumentAccount.capture());
        assertEquals(accountNumber, argumentAccount.getValue().getAccountNumber());
        assertEquals(0.0, argumentAccount.getValue().getBalance(),0.001);
      //  Assert.assertEquals(1000,argumentAccount.getValue().getTimeStamp(),0.001);

    }

    @Test
    public void testGetAccountFromDB()
    {
        ArgumentCaptor<String> argumentAccountNumber = ArgumentCaptor.forClass(String.class);
        BankAccountDTO account = BankAccount.openAccount(accountNumber);

        when(mockAccountDao.getAccount(accountNumber)).thenReturn(account);
        BankAccountDTO accountFromDB=  BankAccount.getAccount(accountNumber);
        verify(mockAccountDao).getAccount(argumentAccountNumber.capture());
        assertEquals(accountNumber,argumentAccountNumber.getValue());
        assertEquals(0,accountFromDB.getBalance(),0.001);
    }


}
