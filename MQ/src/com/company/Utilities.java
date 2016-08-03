package com.company;

/**
 * Created by brajaraman on 03/08/2016.
 */
public class Utilities {

    public static String ReverseOperation(String oper)
    {
        switch (oper)
        {
            case "Sell":
                return "Buy";
            case "Buy":
                return"Sell";
        }
        return "Sell";
    }

    public static boolean OrderStatus(int diff, String type)
    {
        switch (type)
        {
            case "Buy":
                if(diff >= 0)
                    return true;
                else
                return false;
            case "Sell":
                if(diff <= 0)
                    return true;
                else
                    return false;
        }
        return false;
    }

}
