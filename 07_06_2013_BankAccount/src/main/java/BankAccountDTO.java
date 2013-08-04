/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/7/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDTO {
    private final String accountNumber  ;
    private double balance;

    public BankAccountDTO(String accountNumber) {
        this.accountNumber=accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
            return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
