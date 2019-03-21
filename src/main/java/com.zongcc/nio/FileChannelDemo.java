package com.zongcc.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下
 * Created by chunchengzong on 2017-02-22.
 */
public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("d:\\test.txt", "rw");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        int read = fileChannel.read(byteBuffer);
        while (read != -1) {
            System.out.println("read-------------------------------->");
            //flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            //clear()方法，position将被设回0，limit被设置成 capacity的值。换句话说，Buffer被清空了
            byteBuffer.clear();
            //compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity
            //byteBuffer.compact();
            read = fileChannel.read(byteBuffer);
            System.out.print("\n");
        }
        fileChannel.close();
    }
}
