package com.company;

import java.io.*;

public class Ticks {

    private static BufferedReader reader;
    static Ticks obj = null;
    public static Ticks getInstance(String fname)throws Exception {
        if(obj == null)
        {
            obj = new Ticks(fname);
        }

        return obj;
    }
    private Ticks(String fname)throws Exception
    {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fname),"UTF-16"));;
    }

    public TickData GetTickData()throws Exception
    {
        TickData dt = null;
        String ln = null;
        while((ln = reader.readLine())!=null) {
            String[] tokens = ln.split(",");
            if (tokens.length > 1) {
                dt = new TickData(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]), tokens[2]);
                return dt;
            }else
                return null;
        }
        return null;
    }
}



    /*
    Logic To Replace Reduntant Data
    -------------------------------
    PrintStream out = new PrintStream("temp123.txt");
    String ln = null;
    String previous = "";
    int count =0, total=0;
while((ln = in.readLine())!=null)
        {
        String[] tokens = ln.split(",");
        if(tokens.length >1)
        {
        total++;
        if(!previous.equals(tokens[2]))
        {
        out.println(ln);
        previous = tokens[2];
        TickData dt = new TickData(Double.parseDouble(tokens[0]),Double.parseDouble(tokens[1]),tokens[2]);
        }
        else
        {
        count++;
        System.out.println(ln);
        }
        }
        }



 */
