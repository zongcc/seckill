package com.zongcc.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 复制一个文件
 * @author chunchengzong
 * @date 2018-08-08 15:38
 **/
public class FileNIO {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("D:\\test.txt");
        FileChannel fc = fin.getChannel();
        FileOutputStream fout = new FileOutputStream( "D:\\test-out.txt" );
        FileChannel fo = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            buffer.clear();
            int read = fc.read(buffer);
            if (read == -1){
                break;
            }
            buffer.flip();
            fo.write(buffer);
        }
        fc.close();
        fin.close();
        fo.close();
        fout.close();
    }
}