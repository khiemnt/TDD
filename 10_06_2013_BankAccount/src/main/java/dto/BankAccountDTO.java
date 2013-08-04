package dto;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/10/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDTO {
    private String accountNumber;
    private double balance;

    public BankAccountDTO(String accountNumber) {
        this.accountNumber=accountNumber;
    }

    public BankAccountDTO(String accountNumber, double balance) {
        this.accountNumber=accountNumber;
        this.balance =balance;
    }

    public double getBalance() {
        return balance;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
