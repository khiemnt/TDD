package dto;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/18/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountDTO {
    private String accountNumber;
    private double balance;
    private long timeStamp;
    private static Calendar calendar=Calendar.getInstance();
    private  String description;


    public BankAccountDTO(String accountNumber) {
        this.accountNumber=accountNumber;
        this.balance=0;
        this.timeStamp=calendar.getTimeInMillis();


    }

    public BankAccountDTO(String accountNumber, double balance,long timeStamp) {
        this.accountNumber=accountNumber;
        this.balance=balance;
        this.timeStamp=timeStamp;
    }

    public static void setCalendar(Calendar calendar) {
        BankAccountDTO.calendar = calendar;
    }

    public static Calendar getCalendar() {
        return calendar;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getDescription()
    {
        return description;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public void setTimeStamp(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
