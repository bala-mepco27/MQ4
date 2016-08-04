package com.company;

import javax.jws.soap.SOAPBinding;
import javax.rmi.CORBA.Util;
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

    public static int debug_count = 0;

    public static void UpdateAccount(TickData dt) {
        Current = Capital;
        debug_count++;
        if(debug_count == 10638)
            System.out.println(debug_count);
        if (Orders.size() > 1) {
            for (OrderUnit unit : Orders) {
                double price = 0.0;
                if (unit.type.equals("Sell"))
                    price = Data.Buy;
                else
                    price = Data.Sell;

                int val  = (int) Math.round(price * 100000);
                int diff = (val - (int) (unit.price * 100000));
                if (Utilities.OrderStatus(diff, unit.type)) {
                    Current = Current + (unit.vol * Math.abs(diff));
                } else {
                    Current = Current - (unit.vol * Math.abs(diff));
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
        if(Used > 15.0)
            System.out.println(Used);
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
        {
            OrderUnit unit = Orders.get(Orders.size() - 1);
            double diff = 0.0;
            if(unit.type.equals("Sell"))
                diff = Data.Buy - unit.price;
            else
                diff = Data.Sell - unit.price;

            int diffval = (int)(diff * 100000);

            if(Utilities.OrderStatus(diffval,unit.type))
            {
                Current = Current + Math.abs(diffval);
            }
            else {
                Current = Current - Math.abs(diffval);
            }

            Orders.remove(Orders.size() - 1);
            UpdateUsed();
        }else
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


