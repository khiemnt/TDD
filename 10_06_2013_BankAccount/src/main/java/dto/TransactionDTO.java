package dto;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 6/10/13
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionDTO {
    private final String accountNumber;
    private final double amount;
    private final String description;
    private boolean executed= false;
    private long timestamp;
    static Calendar calendar=Calendar.getInstance();
    public TransactionDTO(String accountNumber, double amount, String description) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
        this.timestamp=calendar.getTimeInMillis();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isExecuted() {
        return executed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Calendar calendar) {
        TransactionDTO.calendar = calendar;
    }
}
