package com.zongcc.encodingDetector;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;

/**
 * juniversalchardet
   https://code.google.com/archive/p/juniversalchardet/
 * Created by chunchengzong on 2017-08-18.
 */
public class JuniversalchardetEncodeDetector {
    public static void main(String[] args) throws java.io.IOException
    {
        byte[] buf = new byte[4096];
        String fileName ="C:\\Users\\chunchengzong\\Desktop\\utf8bom.txt";
        FileInputStream fis = new FileInputStream(fileName);

        // (1)
        UniversalDetector detector = new UniversalDetector(null);

        // (2)
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();

        // (4)
        String encoding = detector.getDetectedCharset();
        System.out.println("===== "+encoding);
        if (encoding != null) {
            System.out.println("Detected encoding = " + encoding);
            if (!encoding.equals("UTF-8")) {
                transform(new File(fileName), encoding, new File(fileName+".utf8"), "UTF-8");
                System.out.println("generating " + fileName+".utf8");
            }
        } else {
            System.out.println("No encoding detected.");
        }

        // (5)
        detector.reset();
    }

    public static void transform(File source, String srcEncoding, File target, String tgtEncoding) throws IOException {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(source),srcEncoding));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), tgtEncoding));
            char[] buffer = new char[16384];
            int read;
            while ((read = br.read(buffer)) != -1)
                bw.write(buffer, 0, read);
        } finally {
            try {
                if (br != null)
                    br.close();
            } finally {
                if (bw != null)
                    bw.close();
            }
        }
    }
}
