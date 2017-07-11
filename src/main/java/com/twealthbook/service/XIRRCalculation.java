package com.twealthbook.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Reference https://stackoverflow.com/questions/36789967/java-program-to-calculate-xirr-without-using-excel-or-any-other-library

public class XIRRCalculation {

    public static final double tol = 0.001;

    public static double dateDiff(Date d1, Date d2){
        long day = 24*60*60*1000;

        return (d1.getTime() - d2.getTime())/day;
    }

    public static double f_xirr(double p, Date dt, Date dt0, double x) {
        return p * Math.pow((1.0 + x), (dateDiff(dt0,dt) / 365.0));
    }

    public static double df_xirr(double p, Date dt, Date dt0, double x) {
        return (1.0 / 365.0) * dateDiff(dt0,dt) * p * Math.pow((x + 1.0), ((dateDiff(dt0,dt) / 365.0) - 1.0));
    }

    public static double total_f_xirr(double[] payments, Date[] days, double x) {
        double resf = 0.0;

        for (int i = 0; i < payments.length; i++) {
            resf = resf + f_xirr(payments[i], days[i], days[0], x);
        }

        return resf;
    }

    public static double total_df_xirr(double[] payments, Date[] days, double x) {
        double resf = 0.0;

        for (int i = 0; i < payments.length; i++) {
            resf = resf + df_xirr(payments[i], days[i], days[0], x);
        }

        return resf;
    }

    public static double Newtons_method(double guess, double[] payments, Date[] days) {
        double x0 = guess;
        double x1 = 0.0;
        double err = 1e+100;

        while (err > tol) {
            x1 = x0 - total_f_xirr(payments, days, x0) / total_df_xirr(payments, days, x0);
            err = Math.abs(x1 - x0);
            x0 = x1;
        }

        return x0;
    }

    //private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static Date strToDate(String str){
        try {
            return sdf.parse(str);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static void main(String... args) {
        double[] payments = {-50000.00,-52773.00,-10000.00,-20000.00,-15000.00,-15000.00,-10000.00,-15985.70,-125000.00,-100000.00,-100000.00,-80000.00,-100000.00,-50000.00,23773.00,-100000.00,985976.69}; // payments
        Date[] days = {strToDate("2014-09-01"),strToDate("2014-12-01"),strToDate("2015-02-02"),strToDate("2015-04-01"),strToDate("2015-05-11"),strToDate("2015-08-28"),strToDate("2015-09-28"),strToDate("2015-10-06"),strToDate("2015-11-17"),strToDate("2015-12-03"),strToDate("2016-04-21"),strToDate("2016-04-29"),strToDate("2016-09-19"),strToDate("2016-11-17"),strToDate("2017-03-08"),strToDate("2017-04-03"),strToDate("2017-07-11")}; // days of payment (as day of year)
        //Date[] days = {strToDate("01/09/2014"),strToDate("01/12/2014"),strToDate("02/02/2015"),strToDate("01/04/2015"),strToDate("11/05/2015"),strToDate("28/08/2015"),strToDate("28/09/2015"),strToDate("06/10/2015"),strToDate("17/11/2015"),strToDate("03/12/2015"),strToDate("21/04/2016"),strToDate("29/04/2016"),strToDate("19/09/2016"),strToDate("17/11/2016"),strToDate("08/03/2017"),strToDate("03/04/2017"),strToDate("11/07/2017")}; // days of payment (as day of year)
        double xirr = Newtons_method(0.1, payments, days);

        System.out.println("XIRR value is " + xirr);
    }
}
