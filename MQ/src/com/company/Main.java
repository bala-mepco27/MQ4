package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by brajaraman on 02/08/2016.
 */

class MoneyPair
{
    public Calendar BidTime;
    public Calendar SellTime;
    public double Rate;

    public void DeleteEverything()
    {
        BidTime = null;
        SellTime = null;
        Rate = 0.0;
    }



/*
    MoneyPair(Calendar bt, Calendar st, double rt)
    {
        this.BidTime = bt;
        this.SellTime = st;
        this.Rate = rt;
    }*/
}


public class Main {

    public static void main(String ar[])throws Exception
    {
        String oper = "Sell";
        double price = 0.0;
        double cprice = 0.0;
        Ticks tc = Ticks.getInstance("C:\\Users\\brajaraman\\AppData\\Roaming\\MetaQuotes\\Terminal\\D0E8209F77C8CF37AD8BF550E51FF075\\MQL5\\Files\\Price_List - Copy (2).txt");
        TickData dt = null;
        ArrayList<MoneyPair> mp = new ArrayList<MoneyPair>();
        MoneyPair mr = new MoneyPair();
        mr.DeleteEverything();
        while((dt = tc.GetTickData())!= null)
        {
            if(oper.equals("Sell"))
            {
                cprice = dt.Sell;
                mr.SellTime = dt.date;
            }
            else
            {
                cprice = dt.Bid;
                mr.BidTime = dt.date;
            }

            double diff = Math.abs(Math.abs(cprice*) - Math.abs(price));
            if(diff)

            if(price == 0.0 || price == cprice)
            {
                mr.Rate = cprice;
                price = cprice;
                cprice = 0.0;
                switch (oper)
                {
                    case "Sell":
                        oper = "Buy";
                        break;
                    case "Buy":
                        oper = "Sell";
                        break;
                }

                if((mr.SellTime != null) && (mr.BidTime != null))
                {
                    price = 0.0;
                    mp.add(mr);
                    mr.DeleteEverything();
                }
            }
            //tc = Ticks.getInstance("blah");
        }
    }

}
