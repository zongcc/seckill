package com.zongcc.nio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chunchengzong on 2017-02-22.
 */
public class FileInputStreamDemo {
    public static void main(String[] args) throws IOException {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("d:\\test.txt"));

            byte[] buf = new byte[1024];
            int bytesRead = in.read(buf);
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; i++)
                    System.out.print((char) buf[i]);
                bytesRead = in.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
