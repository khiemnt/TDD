/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/8/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDTO {
    private String accountNumber;
    private double balance;

    public BankAccountDTO(String accountNumber) {
        this.accountNumber=accountNumber;
    }

    public BankAccountDTO(BankAccountDTO bankAccountDTO) {
        this.accountNumber=bankAccountDTO.getAccountNumber();
        this.balance=bankAccountDTO.getBalance();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccountDTO that = (BankAccountDTO) o;

        if (Double.compare(that.balance, balance) != 0) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;

        return true;
    }

}
