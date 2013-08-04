/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/8/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {
    private static BankAccountDao bankAccountDao;

    public static BankAccountDTO openAccount(String accountNumber) {
        BankAccountDTO account=new BankAccountDTO(accountNumber);
        bankAccountDao.save(account);
        return account;
    }

    private static void save(BankAccountDTO account) {
       bankAccountDao.save(account);
    }

    public static void setBankAccountDao(BankAccountDao bankAccountDao) {
        BankAccount.bankAccountDao = bankAccountDao;
    }

    public static BankAccountDao getBankAccountDao() {
        return bankAccountDao;
    }

    public static void deposit(BankAccountDTO account, int amount) {
        account.setBalance(account.getBalance()+amount);
        BankAccount.save(account);
    }

    public static BankAccountDTO getAccount(String accountNumber) {

        return  bankAccountDao.getAccount(accountNumber);
    }

    public static void withdraw(BankAccountDTO account, int amount) {
        account.setBalance(account.getBalance()-amount);
        BankAccount.save(account);
    }
}
