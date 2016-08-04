package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import static com.company.Utilities.*;
import static com.company.Utilities.ReverseOperation;

/**
 * Created by brajaraman on 02/08/2016.
 */


public class Main {

    public static void main(String ar[])throws Exception
    {
        String oper = "Sell";
        double price = 0.0;
        double cprice = 0.0;
        Ticks tc = Ticks.getInstance("C:\\Users\\brajaraman\\AppData\\Roaming\\MetaQuotes\\Terminal\\D0E8209F77C8CF37AD8BF550E51FF075\\MQL5\\Files\\Price_List - Copy (4).txt");
        TickData dt = null;
        ArrayList<MoneyPair> mp = new ArrayList<MoneyPair>();
        MoneyPair mr = new MoneyPair();
        mr.DeleteEverything();
        Calendar start = tc.GetTickData().date;
        Calendar end = null;
        int good=0,bad =0;
        while((dt = tc.GetTickData())!= null)
        {
            end = dt.date;
            if(oper.equals("Sell"))
            {
                cprice = dt.Sell;
                mr.SellTime = dt.date;
            }
            else
            {
                cprice = dt.Buy;
                mr.BuyTime = dt.date;
            }

            int i = (int)(cprice * 100000);
            int j = (int)(price * 100000);
            int k = (i-j);
            int diff = (int)k;
            if( Math.abs(diff) > 100  && (price != 0 && cprice !=0))
            {
                if(Utilities.OrderStatus(diff,ReverseOperation(oper)))
                {
                    System.out.println("***** GOOD - The Diff is greater than 100");
                    good++;
                }
                else
                {
                    System.out.println("????? BAD - The Diff is greater than 100");
                    oper = ReverseOperation(oper);
                    if(oper.equals("Sell"))
                        cprice = dt.Sell;
                    else
                        cprice = dt.Buy;
                    bad++;
                }
                //MarginCalculator.PrintCurrentStatus(true);
                MarginCalculator.SellLastOrder(price);
                price = 0.0;

            }

            if((price == 0.0 || price == 1.09689)&&(cprice == 1.09698 || cprice == 1.09689))
                System.out.println(cprice);

            if(price == 0.0 || price == cprice)
            {
                mr.Rate = cprice;
                if(MarginCalculator.Order(0.1,oper)) {
                    price = cprice;
                }else
                {
                    System.out.println("_-------------------- Luck has run out");
                    System.exit(1);
                }
                MarginCalculator.PrintCurrentStatus(false);

                cprice = 0.0;
                oper = ReverseOperation(oper);

                if((mr.SellTime != null) && (mr.BuyTime != null))
            {
                price = 0.0;
                mp.add(mr);
                mr.DeleteEverything();
            }
            }
        }
        System.out.println("Pairs: "+mp.size());

        System.out.println("Hours : " + (end.getTimeInMillis() - start.getTimeInMillis())/(3600 * 1000));

        System.out.println("Good : " + good + "  Bad : "+ bad);
    }

}
