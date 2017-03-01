package com.zongcc.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * Created by chunchengzong on 2017-02-22.
 */
public class FileChannel {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("d:\\test.txt","rw");
        java.nio.channels.FileChannel fileChannel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        int read = fileChannel.read(byteBuffer);
        while (read!=-1){
            System.out.println("read----------->");
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()){
                System.out.print((char)byteBuffer.get());
            }
            byteBuffer.clear();
            read = fileChannel.read(byteBuffer);
            System.out.print("\n");
        }
        fileChannel.close();
    }
}
