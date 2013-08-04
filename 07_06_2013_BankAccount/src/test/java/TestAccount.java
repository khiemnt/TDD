import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/7/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestAccount {
    private BankAccountDao mockAccountDao= mock(BankAccountDao.class);
    @Before
    public void setUp(){
        reset(mockAccountDao);
        BankAccount.setBankAccountDao(mockAccountDao);
    }

    @Test
    public  void testOpenOneAccountAndIsPersistentToDB(){

        BankAccount.openAccount("1233456");
        ArgumentCaptor<BankAccountDTO> argument=ArgumentCaptor.forClass(BankAccountDTO.class) ;
        verify(mockAccountDao).save(argument.capture());
        assertEquals("1233456",argument.getValue().getAccountNumber());
        assertEquals(0,argument.getValue().getBalance(),0.001);
    }
    @Test
    public void testNewTransactionAndSaveToDB(){
        BankAccountDTO account= BankAccount.openAccount("1233456");
        ArgumentCaptor<BankAccountDTO> argument=ArgumentCaptor.forClass(BankAccountDTO.class);
        BankAccount.deposit(account,1000);
        verify(mockAccountDao,times(2)).save(argument.capture());
        List<BankAccountDTO> bankAccountDTOList=argument.getAllValues() ;
        assertEquals("1233456",bankAccountDTOList.get(1).getAccountNumber());
        assertEquals(1000,bankAccountDTOList.get(1).getBalance(),0.01);
    }
}
