package dto;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/13/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDTO {

    private String accountNumber;
    private double balance=0;


    public BankAccountDTO(String accountNumber) {
        this.accountNumber=accountNumber;
    }

    public BankAccountDTO(String accountNumber, double balance) {
        this.accountNumber=accountNumber;
        this.balance=balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
