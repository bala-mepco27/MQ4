package com.company;

import java.util.Calendar;

/**
 * Created by brajaraman on 03/08/2016.
 */
public class OrderUnit {

    public String type;
    public double vol;
    public double price;
    public Calendar date;

    public OrderUnit(String ty, double vol, double pr, Calendar dt)
    {
        this.type = ty;
        this.vol = vol;
        this.price = pr;
        this.date = dt;
    }
}
