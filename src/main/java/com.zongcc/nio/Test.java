package com.zongcc.nio;

import com.zongcc.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Test {

    public static void main(String[] args) throws InterruptedException {
        //String path = "/opt/project/mp.xlsx";

        Date yesterDay = DateUtil.addDays(new Date(),-1);
        String date = DateUtil.date2str(yesterDay,"yyyyMMdd");
        System.out.println(date);

        String str = "ads_20170718.tsv";
        //str.split("-");
        int a = str.indexOf("ads_");
        int b = str.indexOf(".tsv");
        System.out.println(str.substring(a+"ads_".length(),b));
        System.out.println("中间的日期"+DateUtil.str2date(str.substring(4,b),"yyyyMMdd"));
        //System.out.println("asdfasdfads  "+Integer.valueOf(str));
       // System.out.println("asdfasdfads  "+ new Integer(str));

        String path = "C:\\Users\\chunchengzong\\Desktop\\ads_20170719.tsv";
        BufferedReader br = null;
        try {
            File file = new File(path);
            //if(file.exists()) file.delete();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

            String line = br.readLine();
            while (StringUtils.isNotBlank(line)) {
                //System.out.println("======"+line);
                String[] array = line.split("\t");
                System.out.println("======"+array[0]);
                System.out.println("======"+array[1]);
                System.out.println("======"+array[2]);
                System.out.println("======"+array[3]);
                System.out.println("======"+array[4]);
                System.out.println("======"+array[5]);
                System.out.println("======"+array[6]);

                System.out.println("++++++++++++++++++++++++++++");
                line = br.readLine();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<Integer> list = new ArrayList<>();
        list.add(1);list.add(2);list.add(3);list.add(4);list.add(5);
        for(Integer i:list){
            try {
                excuteMethod(i);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
            System.out.println("===== "+i);
        }

        String aL="ads_20170120_diff.tsv";
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(aL);
        String alm = m.replaceAll("").trim();
        System.out.println(alm.trim());


    }

    private static void excuteMethod(int i) throws InterruptedException {
        if(i==2){
            Thread.sleep(100);
            int j = 1/0;
        }else {
            Thread.sleep(100);
        }
    }


}