package com.qsoft.BankAccount.persistence.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 1:40 PM
 */
@Entity
@Table(name = "transaction")
@SequenceGenerator(name = "seq_id", sequenceName = "seq_id", initialValue = 1, allocationSize = 1)

public class TransactionEntity
{
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_id")
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private double amount;

    @Column(name = "time_stamp")
    private long timeStamp;

    private static Calendar calendar = Calendar.getInstance();

    public TransactionEntity()
    {
    }

    public TransactionEntity(String accountNumber, double amount, String description)
    {
        this.accountNumber = accountNumber;
        this.description = description;
        this.amount = amount;
        this.timeStamp = calendar.getTimeInMillis();
    }

    public TransactionEntity(String accountNumber, double amount,long timeStamp, String description)
    {
        this.accountNumber = accountNumber;
        this.description = description;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public static void setCalendar(Calendar calendar)
    {
        TransactionEntity.calendar = calendar;
    }
}