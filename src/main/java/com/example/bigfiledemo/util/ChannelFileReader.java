package com.example.bigfiledemo.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelFileReader {
    private FileInputStream fileIn;
    private ByteBuffer byteBuf;
    private byte[] array;

    public ChannelFileReader(String fileName, int arraySize) throws IOException {
        this.fileIn = new FileInputStream(fileName);
        this.byteBuf = ByteBuffer.allocate(arraySize);
    }

    public int read() throws IOException {
        FileChannel fileChannel = fileIn.getChannel();
        int bytes = fileChannel.read(byteBuf);// 读取到ByteBuffer中
        if (bytes != -1) {
            array = new byte[bytes];// 字节数组长度为已读取长度
            byteBuf.flip();
            byteBuf.get(array);// 从ByteBuffer中得到字节数组
            byteBuf.clear();
            return bytes;
        }
        return -1;
    }

    public void close() throws IOException {
        fileIn.close();
        array = null;
    }

    public static void main(String[] args) throws IOException {
        ChannelFileReader reader = new ChannelFileReader("D:\\60G.txt", 65536);
        long start = System.nanoTime();
        while (reader.read() != -1) ;
        long end = System.nanoTime();
        reader.close();
        System.out.println("ChannelFileReader: " + (end - start) * 1.0 / 1_000_000_000);
    }
}
