import dao.BankAccountDao;
import dto.BankAccountDTO;
import org.junit.Before;
import org.junit.Test;
import service.BankAccount;

import static org.junit.Assert.assertEquals;

import org.mockito.*;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/10/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class testBankAccount {

    private BankAccountDao mockAccountDao = mock(BankAccountDao.class);
    public String accountNumber = "1234567";

    @Before
    public void setUp() {
        reset(mockAccountDao);
        BankAccount.setBankAccountDao(mockAccountDao);
    }

    @Test
    public void testNewAccountWithPersistenceToDB() {
        BankAccount.openAccount(accountNumber);
        ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
        verify(mockAccountDao).save(argument.capture());
        assertEquals(accountNumber, argument.getValue().getAccountNumber());
        assertEquals(0, argument.getValue().getBalance(), 0.001);

    }

    @Test
    public void testGetAccountFromDB() {
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        BankAccountDTO accountDTO = new BankAccountDTO(accountNumber,1000.0);
        when(mockAccountDao.getAccount(accountNumber)).thenReturn(accountDTO);
        BankAccountDTO accountFromDB = BankAccount.getAccount(accountNumber);
        verify(mockAccountDao).getAccount(argument.capture());
        assertEquals(argument.getValue(), accountFromDB.getAccountNumber());
        assertEquals(1000, accountFromDB.getBalance(),0.001);

    }

}
