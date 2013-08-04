import dao.BankAccountDao;
import dto.BankAccountDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import service.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/13/13
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class testBankAccount {
    private String accountNumber = "1234567890";
    private BankAccountDao mockAccountDao = mock(BankAccountDao.class);

    @Before
    public void setUp() {
        reset(mockAccountDao);
        BankAccount.setBankAccountDao(mockAccountDao);
    }

    @Test
    public void testNewAccountAndPersistToDB() {
        ArgumentCaptor<BankAccountDTO> argumentAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccount.openAccount(accountNumber);
        verify(mockAccountDao).save(argumentAccount.capture());
        assertEquals(accountNumber, argumentAccount.getValue().getAccountNumber());
        assertEquals(0.0, argumentAccount.getValue().getBalance(), 0.001);
    }

    @Test
    public void testGetAccountFromDB() {
        ArgumentCaptor<String> argumentAccountNumber = ArgumentCaptor.forClass(String.class);
        BankAccountDTO bankAccountDTO = new BankAccountDTO(accountNumber, 1000.0);
        when(mockAccountDao.getAccount(accountNumber)).thenReturn(bankAccountDTO);
        BankAccountDTO accountFromDB = BankAccount.getAccount(accountNumber);
        verify(mockAccountDao).getAccount(argumentAccountNumber.capture());

        assertEquals(accountNumber,argumentAccountNumber.getValue());
        assertEquals(1000,accountFromDB.getBalance(),0.001);
    }
}
