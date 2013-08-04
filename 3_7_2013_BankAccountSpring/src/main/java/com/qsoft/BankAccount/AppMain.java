package com.qsoft.BankAccount;

import com.qsoft.BankAccount.persistence.dao.BankAccountDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: khiemnt
 * Date: 7/11/13
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppMain {
    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        BankAccountDAO bankAccountDAO = (BankAccountDAO) appContext.getBean("bankAccountDAO");
    }

}
