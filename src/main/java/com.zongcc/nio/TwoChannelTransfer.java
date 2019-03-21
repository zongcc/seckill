package com.zongcc.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。如果源通道的剩余空间小于 count 个字节，
 * 则所传输的字节数要小于请求的字节数。
 * 此外要注意，在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。
 * 因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
 *
 * @author chunchengzong
 * @date 2019-01-03 11:05
 **/
public class TwoChannelTransfer {
    public static void main(String[] args) throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("d:\\fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("d:\\toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long size = fromChannel.size();
        //toChannel.transferFrom(fromChannel, position, size);
        fromChannel.transferTo(position, size, toChannel);

        fromFile.close();
        toFile.close();
        fromChannel.close();
        toChannel.close();
    }
}