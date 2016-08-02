package com.company;

/**
 * Created by brajaraman on 02/08/2016.
 */
public class Main {

    public static void main(String ar[])throws Exception
    {
        Ticks tc = Ticks.getInstance("C:\\Users\\brajaraman\\AppData\\Roaming\\MetaQuotes\\Terminal\\D0E8209F77C8CF37AD8BF550E51FF075\\MQL5\\Files\\Price_List - Copy (2).txt");
        TickData dt = null;
        while((dt = tc.GetTickData())!= null)
        {
            System.out.println(dt.Bid);
            //tc = Ticks.getInstance("blah");
        }
    }

}
