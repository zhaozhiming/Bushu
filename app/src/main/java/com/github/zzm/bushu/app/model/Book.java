package com.github.zzm.bushu.app.model;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {
    private String enName;
    private String zhName;
    private String borrowPeople;
    private Date returnDate;

    public Book(String enName, String zhName, String borrowPeople, Date returnDate) {
        this.enName = enName;
        this.zhName = zhName;
        this.borrowPeople = borrowPeople;
        this.returnDate = returnDate;
    }

    public String getEnName() {
        return enName;
    }

    public String getBorrowPeople() {
        return borrowPeople;
    }

    public String getZhName() {
        return zhName;
    }

    public int returnDays() {
        return Days.daysBetween(DateTime.now().withTimeAtStartOfDay(),
                new DateTime(returnDate).withTimeAtStartOfDay()).getDays();
    }
}
