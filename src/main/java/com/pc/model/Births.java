package com.pc.model;

public class Births {
    protected String year;
    protected Integer amount;


    public Births(String year, Integer amount) {
        super();
        this.year = year;
        this.amount = amount;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


}
