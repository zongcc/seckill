package com.zongcc.encodingDetector;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.*;

/**
 * icu4j方式解析编码，setText()方法需要InputStream特别的支持mark,reset方法
 * Created by chunchengzong on 2017-08-18.
 */
public class Icu4jEncodeDetector {
    /**
     * 获取编码
     * @throws IOException
     * @throws Exception
     */
    public static String getEncode(byte[] data,String url){
        CharsetDetector detector = new CharsetDetector();
        detector.setText(data);
        CharsetMatch match = detector.detect();
        String encoding = match.getName();
        System.out.println("The Content in " + match.getName());
        CharsetMatch[] matches = detector.detectAll();
        System.out.println("All possibilities");
        for (CharsetMatch m : matches) {
            System.out.println("CharsetName:" + m.getName() + " Confidence:"
                    + m.getConfidence());
        }
        return encoding;
    }
    public static String getEncode(InputStream data) throws IOException {
        CharsetDetector detector = new CharsetDetector();
        detector.setText(data);
        CharsetMatch match = detector.detect();
        String encoding = match.getName();
        return encoding;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\chunchengzong\\Desktop\\utf8bom.txt");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        System.out.println(Icu4jEncodeDetector.getEncode(in));
    }
}
