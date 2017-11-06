package com.zongcc.staticTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.glaforge.i18n.io.CharsetToolkit;
import com.zongcc.encodingDetector.Icu4jEncodeDetector;
import com.zongcc.model.Seckill;
import com.zongcc.utils.CaculateUtil;
import com.zongcc.utils.DateUtil;
import com.zongcc.utils.JacksonUtil;
import org.apache.any23.encoding.TikaEncodingDetector;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chunchengzong on 2017-05-09.
 */
public class Test {

    public static void main(String[] args) throws ParseException, IOException {

        List list = new ArrayList<Seckill>();
        Seckill seckill = new Seckill();
        seckill.setName("test");
        seckill.setCreateTime(new Date());
        seckill.setNumber(1);
        list.add(seckill);
        String str = JacksonUtil.toJson(list);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());

        byte[] bytes = IOUtils.toByteArray(byteArrayInputStream);
        List<Seckill> seckills = JacksonUtil.toCollection(new String(bytes), new TypeReference<List<Seckill>>() {});
        for(Seckill s:seckills){
            System.out.println("out object ========"+s.getName());
        }

        //获取系统类型
        System.out.println("-----os name--------"+System.getProperty("os.name"));

        String costStr = CaculateUtil.getLongDivide(36013528L,100000L,5);
        Double costDouble = 0D;
        Double cost = 108.38328D+251.75200D;
        if(StringUtils.isNotBlank(costStr)){
            costDouble = Double.valueOf(costStr);
        }
        if (!costDouble.equals(cost)) {
            System.out.println("costDouble =========不想动相等");
        }else {
            System.out.println("costDouble =========相等");
        }



        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH");
        System.out.println(sdf.format(new Date())+":00:00");


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

        Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9]+$");
        Matcher matcher = pattern.matcher("海贼王12Vv。。");
        System.out.println("中文、英文、数字但不包括下划线等符号 " +matcher.matches());

        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher matchers = p.matcher("ads_20170830_difff.tsv");
        Date dateTime= DateUtil.str2date(matchers.replaceAll("").trim(), "yyyyMMdd");
        System.out.println("------------"+DateUtil.date2str(dateTime));



        System.out.println("=======================================================================");
        File file = new File("C:\\Users\\chunchengzong\\Desktop\\test.txt");
        //BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        FileInputStream ins = new FileInputStream(file);
        FileInputStream in = new FileInputStream(file);
        //BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        String code = "GBK";
        /*if (bb[0] == -17 && bb[1] == -69 && bb[2] == -65){
            code = "UTF-8";
        }*/
        //String charset =  FileCharsetUtil.getFileEncode(in);
        //Charset charset =  Charset.forName(new TikaEncodingDetector().guessEncoding(in));
        //Charset charset =  CharsetToolkit.guessEncoding(file, 4096);
        //BufferedReader bufferedReader = new BufferedReader(new UnicodeReader(ins,charset));
        //BufferedReader bufferedReader = new BufferedReader(new UnicodeReader(ins,charset.name()));
        BufferedReader bufferedReader = new BufferedReader(new UnicodeReader(ins,FileCharsetUtil.getFileEncode(in)));
        //InputStreamReader inputStreamReader = new InputStreamReader(in,code);
        //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        String str = bufferedReader.readLine();
//        while(str != null){
//            System.out.println(str.trim());
//            str = bufferedReader.readLine();
//        }

        Pattern patterns = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9]+$");
        String line = bufferedReader.readLine();
        while (StringUtils.isNotBlank(line)) {
            if(patterns.matcher(line).matches()){
                System.out.println("正常字符 "+line);
                line = bufferedReader.readLine();
            }else {
                System.out.println("特殊字符 "+line);
                break;
            }
        }
        in.close();
        bufferedReader.close();

        FileInputStream inst = new FileInputStream(file);
        System.out.println("文件编码 " + Charset.forName(new TikaEncodingDetector().guessEncoding(inst)));

    }

}
