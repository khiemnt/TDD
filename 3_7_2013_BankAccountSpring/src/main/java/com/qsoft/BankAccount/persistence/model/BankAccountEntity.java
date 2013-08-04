package com.qsoft.BankAccount.persistence.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * User: khiemnt
 * Date: 7/3/13
 * Time: 1:41 PM
 */
@Entity
@Table(name = "bank_account")
@SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", initialValue = 1, allocationSize = 1)
public class BankAccountEntity
{
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "account_id_seq")
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private double balance;

    @Column(name = "time_stamp")
    private long timeStamp;

    private static Calendar calendar = Calendar.getInstance();

    public BankAccountEntity()
    {
    }
    public BankAccountEntity(String accountNumber, double balance, long timeStamp)
    {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.timeStamp = timeStamp;
    }

    public BankAccountEntity(String accountNumber, int balance)
    {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.timeStamp = calendar.getTimeInMillis();
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public static Calendar getCalendar()
    {
        return calendar;
    }

    public static void setCalendar(Calendar calendar)
    {
        BankAccountEntity.calendar = calendar;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }
}
