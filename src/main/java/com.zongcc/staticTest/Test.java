package com.zongcc.staticTest;

import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chunchengzong on 2017-05-09.
 */
public class Test {
    public static void main(String[] args) throws ParseException {

        /*
        计算操作
         */
        BigDecimal total = BigDecimal.TEN;

        BigDecimal a = null;
        if(null != a&& a.compareTo(BigDecimal.ZERO)>0){
            total = total.add(a);
        }
        BigDecimal b = new BigDecimal("10");
        if(null != b&& b.compareTo(BigDecimal.ZERO)>0){
            total = total.add(b);
        }
        System.out.println(total);

        Long l = 10L;
        System.out.println(total.add(BigDecimal.valueOf(l)).longValue());


        /*
        日期计算
         */
        Calendar cale = null;
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取前月的第一天
        cale = Calendar.getInstance();

        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(new Date());
        System.out.println("今日 ： " + dateString);
        Long cashBalance = 10L;
        Long returnBalance = 70L;

        if((null == cashBalance || cashBalance.equals(0L)) && (null == returnBalance||returnBalance.equals(0L))){
            System.out.println(11111111);
        }else{
            System.out.println(2222);
        }

        System.out.println(cashBalance.compareTo(returnBalance));

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        final VolatileExample volatileExample = new VolatileExample();
        volatileExample.set(0L);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100000000;i++)
                volatileExample.getIncrement();
                System.out.println("volatileExample "+volatileExample.get());
            }
        });
        System.out.println("time----->"+(System.currentTimeMillis()-start));
        executorService.shutdown();


        VolatileExample volatileExample2= new VolatileExample();
        long start2 = System.currentTimeMillis();
        for (int i=0;i<100000000;i++)
            volatileExample2.getIncrement();
        System.out.println("volatileExample2 "+volatileExample.get());
        System.out.println("time2----->"+(System.currentTimeMillis()-start2));



        StringBuilder sb = new StringBuilder();
        sb.append("123.126.70.239");
        sb.append("zh36.163.com");
        sb.append("1");
        sb.append("1");
        sb.append("kSyWTavkekWXVQRo".substring(8));
        String mySign = EncryptUtil.EncoderByMD5(sb.toString(), "UTF-8");
        System.out.println("mySign=========="+mySign);

        //判断时间
        Date current = new Date();
        String dateStr = "2017-01-01";
        Date date = DateUtils.parseDate(dateStr,new String[]{"yyyy-MM-dd"});
        System.out.println("============================"+current.equals(date));

    }
}
