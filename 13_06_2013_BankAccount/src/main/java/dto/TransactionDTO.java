package dto;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/17/13
 * Time: 8:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionDTO {
    private String accountNumber;
    private double amount;
    private boolean execute=false;
    private long timeStamp;
    private final String description;
    static Calendar calendar=Calendar.getInstance();

    public TransactionDTO(String accountNumber, double amount, String description) {
        this.accountNumber=accountNumber;
        this.amount=amount;
        this.description=description;
        this.timeStamp=calendar.getTimeInMillis();
    }

    public static void setCalendar(Calendar calendar) {
        TransactionDTO.calendar = calendar;
    }

    public static Calendar getCalendar() {
        return calendar;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isExecute() {
        return execute;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
