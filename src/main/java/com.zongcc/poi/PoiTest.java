package com.zongcc.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chunchengzong
 * @date 2018-12-28 17:07
 **/
public class PoiTest {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        System.out.println("===============================================time:" + new Date());

        //SXSSF方式处理导出问题，快速 节省内存 生成临时文件
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        Sheet sheet = wb.createSheet();
        for (int i = 0; i < 1048576; i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue("我是第:" + i + " 条数据");
        }
        File file = new File("C:\\Users\\Administrator\\Desktop" + File.separator + "MaxRows.xlsx");
        OutputStream os;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            wb.dispose();//删除临时文件
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long end1 = System.currentTimeMillis();
        System.out.println("===============================================time:" + (end1 - start) + " " + new Date());


        //XSSF方式处理导出问题
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        Sheet sheet1 = xssfWorkbook.createSheet();

        for (int i = 0; i < 1048576; i++) {
            Row row = sheet1.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue("我是第:" + i + " 条数据");
        }
        File file1 = new File("C:\\Users\\Administrator\\Desktop" + File.separator + "MaxRows1.xlsx");
        OutputStream os1;
        try {
            os1 = new FileOutputStream(file1);
            xssfWorkbook.write(os1);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("===============================================time:" + (end2 - end1) + " " + new Date());


        SXSSFWorkbook wb2 = new SXSSFWorkbook(100);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 2048576; i++) {
            data.add("我是第:" + i + " 条数据");
        }

        int ceil = (int) Math.ceil(data.size() * 1.0 / 1000000);
        for (int i = 1; i <= ceil; i++) {
            Sheet sheet2 = wb2.createSheet(i + "");
            List<String> tmpList = null;
            if (data.size() >= 1000000 * i) {
                tmpList = data.subList(1000000 * (i - 1), 1000000 * i);
            } else {
                tmpList = data.subList(1000000 * (i - 1), data.size());
            }
            for (int j = 0; j < tmpList.size(); j++) {
                Row row = sheet2.createRow(j);
                Cell cell = row.createCell(0);
                cell.setCellValue(tmpList.get(j));
            }
        }


        File file2 = new File("C:\\Users\\Administrator\\Desktop" + File.separator + "MaxRows2.xlsx");
        OutputStream os2;
        try {
            os2 = new FileOutputStream(file2);
            wb2.write(os2);
            wb2.dispose();//删除临时文件
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}