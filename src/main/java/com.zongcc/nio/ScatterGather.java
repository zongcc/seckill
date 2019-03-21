package com.zongcc.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，Channel将从Channel中读取的数据
 * “分散（scatter）”到多个Buffer中。
 * 聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据
 * “聚集（gather）”后发送到Channel。
 *
 * @author chunchengzong
 * @date 2019-01-02 17:46
 **/
public class ScatterGather {
    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("d:\\test.txt", "rw");
        RandomAccessFile outFile = new RandomAccessFile("d:\\test-out2.txt", "rw");
        FileChannel channel = file.getChannel();
        FileChannel outFileChannel = outFile.getChannel();
        //buffer首先被插入到数组，然后再将数组作为channel.read() 的输入参数。read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，
        //当一个buffer被写满后，channel紧接着向另一个buffer中写
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = {header, body};
        long read = channel.read(bufferArray);
        while (read != -1){
            header.flip();
            body.flip();
          /*  //System.out.print(new String(header.array()));
            while (header.hasRemaining()){
                System.out.print((char) header.get());
            }
            System.out.println();
            System.out.println("-------------------------------------------");
            //System.out.println(new String(body.array()));
            while (body.hasRemaining()){
                System.out.print((char) body.get());
            }*/

            //buffers数组是write()方法的入参，write()方法会按照buffer在数组中的顺序，将数据写入到channel，注意只有position和limit之间的数据才会被写入。
            outFileChannel.write(bufferArray);
            header.clear();
            body.clear();
            read = channel.read(bufferArray);
        }
        file.close();
        outFile.close();
        channel.close();
        outFileChannel.close();
    }
}