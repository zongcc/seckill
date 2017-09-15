package com.zongcc.encodingDetector;

import org.apache.any23.encoding.TikaEncodingDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * TikaEncodingDetector方式解析文件编码
 * Created by chunchengzong on 2017-08-18.
 */
public class TikaEncodeDetector {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\chunchengzong\\Desktop\\utf8bom.txt");
        FileInputStream ins = new FileInputStream(file);
        Charset charset =  Charset.forName(new TikaEncodingDetector().guessEncoding(ins));
        System.out.println(charset.name());
    }
}
