package com.zongcc.staticTest;

import java.math.BigDecimal;

/**
 * @author chunchengzong
 * @date 2019-02-26 19:41
 **/
public class TestDemo {

    public static void main(String[] args) {
        String season = "1";
        Integer year = 2019;
        System.out.println(year + season);
        System.out.println(Integer.valueOf(year + season));
        System.out.println(BigDecimal.ZERO.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));

        BigDecimal bd = BigDecimal.valueOf(1).multiply(BigDecimal.valueOf(100));
        BigDecimal bd1 = BigDecimal.valueOf(0).multiply(BigDecimal.valueOf(100));
        System.out.println(bd);
        System.out.println(bd1);
        System.out.println(bd+"%/"+bd1+"%");
    }
}