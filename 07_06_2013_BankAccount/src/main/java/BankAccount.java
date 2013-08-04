/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/7/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {
    private static BankAccountDao bankAccountDao    ;

    public static BankAccountDTO openAccount(String accountNumber) {
        BankAccountDTO account=new BankAccountDTO(accountNumber);
        bankAccountDao.save(account);
        return account;
    }

    public static void setBankAccountDao(BankAccountDao bankAccountDao) {
        BankAccount.bankAccountDao=bankAccountDao;
    }



    public static void deposit(BankAccountDTO account, int amount) {
        account.setBalance(account.getBalance()+amount);
        bankAccountDao.save(account);
    }
}
