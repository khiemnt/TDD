package dto;

import org.hamcrest.StringDescription;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/19/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionDTO {
    private String accountNumber;
    private double amount;
    private long timeStamp;
    private String description;
    static Calendar calendar=Calendar.getInstance();
    public TransactionDTO(String accountNumber, int amount, String description) {
        this.accountNumber=accountNumber;
        this.amount=amount;
        this.timeStamp= calendar.getTimeInMillis();
    }

    public TransactionDTO(String accountNumber, long timeStamp, double amount,String description)
    {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.description=description;
    }

    public static void setCalendar(Calendar calendar) {
        TransactionDTO.calendar = calendar;
    }

    public static Calendar getCalendar() {
        return calendar;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
