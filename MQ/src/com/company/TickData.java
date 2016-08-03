package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by brajaraman on 02/08/2016.
 */
class TickData
{
    double Buy;
    double Sell;
    Calendar date;

    public TickData(double bid, double sell, String dt)
    {
        try {
            this.date = Calendar.getInstance();
            this.date.setTime((new SimpleDateFormat("yyyy.MM.dd HH:mm:ss")).parse(dt));
            this.Buy = bid;
            this.Sell = sell;
        }catch(ParseException exp){exp.printStackTrace();
            this.Buy = -1; this.Sell = -1; this.date = null;}
    }
}