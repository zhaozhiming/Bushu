package com.github.zzm.bushu.app.model;

import java.util.Date;

public class Book {
    private String name;
    private String borrowPeople;
    private Date returnDate;

    public Book(String name, String borrowPeople, Date returnDate) {
        this.name = name;
        this.borrowPeople = borrowPeople;
        this.returnDate = returnDate;
    }

    public String getName() {
        return name;
    }

    public String getBorrowPeople() {
        return borrowPeople;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
