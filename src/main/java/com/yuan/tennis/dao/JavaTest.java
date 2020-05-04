package com.yuan.tennis.dao;

import java.math.BigDecimal;

/**
 * Created by V644593 on 9/19/2015.
 */
public class JavaTest
{
    public static void main(String[] args)
    {
        int a = 5;
        int b = 10;
        BigDecimal bd = new BigDecimal(a * 100.0 / (a + b)).setScale(1, BigDecimal.ROUND_UP);
        System.out.println(bd);
    }

}
