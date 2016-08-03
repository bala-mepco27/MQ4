package com.company;

import java.util.Calendar;

/**
 * Created by brajaraman on 03/08/2016.
 */
public class MoneyPair
{
    public Calendar BuyTime;
    public Calendar SellTime;
    public double Rate;

    public void DeleteEverything()
    {
        BuyTime = null;
        SellTime = null;
        Rate = 0.0;
    }
}