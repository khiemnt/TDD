import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/8/13
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDao {
   public Map<String,BankAccountDTO> accountList=new HashMap<String, BankAccountDTO>();

    public void save(BankAccountDTO account) {
        System.out.println(2);
        if(getAccount(account.getAccountNumber())!=null){
            accountList.put(account.getAccountNumber(),account);
        }

    }

    public BankAccountDTO getAccount(String accountNumber) {
        System.out.println("getacc");
         return new  BankAccountDTO(accountList.get(accountNumber));
    }
}
