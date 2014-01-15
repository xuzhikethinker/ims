package com.ims.webapp.view.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by Administrator on 14-1-14.
 */
public class NumberUtil {
    public static double formatDouble(double value, int decimal) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double formatDoubleWith2Decimal(double value) {
        return formatDouble(value, 2);
    }

    public static double format(double value, int decimal) {
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(decimal);
        String s = ddf1.format(value);
        return Double.parseDouble(s);
    }

    public static void main(String[] args) throws ParseException {
        double x = 23.5455;
        NumberFormat ddf1 = NumberFormat.getNumberInstance();

        ddf1.setMaximumFractionDigits(2);
        String s = ddf1.format(x);
        System.out.print(format(12.654,2));
    }
}
