package com.company;

import javax.jws.soap.SOAPBinding;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by brajaraman on 03/08/2016.
 */
public class MarginCalculator {

    public static double Capital = 1000.0;
    public static double Leverage = 0.01;
    public static double Current = Capital;
    public static double Used = 0.0;
    public static TickData Data = null;
    public static ArrayList<OrderUnit> Orders = new ArrayList<OrderUnit>();
    public static boolean isXM = true;

    public static void UpdateAccount(TickData dt) {
        Current = Capital;
        if (Orders.size() > 1) {
            for (OrderUnit unit : Orders) {
                int val  = (int) Math.round(dt.Sell * 100000);
                int diff = Math.abs(val - (int) (unit.price * 100000));
                if (Utilities.OrderStatus(diff, unit.type)) {
                    Current = Current + (unit.vol * diff);
                } else {
                    Current = Current - (unit.vol * diff);
                }
            }
        }
        Data = dt;
    }

    private static void UpdateUsed() {
        double SellTotal = 0.0;
        double BidTotal = 0.0;
        if (Orders.size() > 0) {
            for (OrderUnit unit : Orders) {
                if (unit.type.equals("Sell")) {
                    SellTotal = SellTotal + (unit.vol / Leverage);
                } else {
                    BidTotal = BidTotal + (unit.vol / Leverage);
                }
            }
            Used = Math.abs(SellTotal - BidTotal);
        }
    }

    public static boolean Order(double vol, String type) {
        double required = vol / Leverage;
        double price = 0.0;

        if (type.equals("Sell"))
            price = Data.Sell;
        else
            price = Data.Buy;

        if (required < (Current - Used)) {
            Orders.add(new OrderUnit(type, vol, price, Calendar.getInstance()));
            Used = Used + required;
            if (isXM) UpdateUsed();
            return true;
        } else {
            return false;
        }

    }

    public static boolean SellLastOrder(double price)
    {
        if(Orders.get(Orders.size()-1).price == price)
            Orders.remove(Orders.size()-1);
        else
        {
            System.out.println("-------------------- Something has gone wrong");
            System.exit(1);
        }
        return true;
    }

    public static void PrintCurrentStatus(boolean printOrders) {
        System.out.println("Capital : " + Capital + " Current : " + Current + " Used : " + Used);
        if(printOrders) {
            for (OrderUnit unit : Orders) {
                System.out.println("Type : " + unit.type + " Vol : " + unit.vol + " Price : " + unit.price /*+ " Date : " + unit.date*/);
            }
        }
    }
}


