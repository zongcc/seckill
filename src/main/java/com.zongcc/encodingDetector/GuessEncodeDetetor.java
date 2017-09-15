package com.zongcc.encodingDetector;

import com.glaforge.i18n.io.CharsetToolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * GuessEncoding中ansi测试不准确
 * Created by chunchengzong on 2017-08-18.
 */
public class GuessEncodeDetetor {

    public static void main(String[] args) throws IOException {
        String fileName ="C:\\Users\\chunchengzong\\Desktop\\utf8bom.txt";
        File file = new File(fileName);
        System.out.println(guessCharset2(file));
    }
    public static Charset guessCharset2(File file) throws IOException {
        return CharsetToolkit.guessEncoding(file, 4096);
    }
}
