package com.zongcc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author chunchengzong
 * @date 2018-03-30 14:56
 **/
public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 读取网络文件落地
     *
     * @param fileUrl  网络资源地址
     * @param savePath
     * @return
     */
    public static void saveUrlAs(String fileUrl, String savePath) throws IOException {
        URL url = new URL(fileUrl);
        /*将网络资源地址传给,即赋值给url*/
        /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        DataInputStream in = new DataInputStream(connection.getInputStream());
        /*此处也可用BufferedInputStream与BufferedOutputStream*/
        DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
        /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/
        byte[] buffer = new byte[4096];
        int count = 0;
        /*将输入流以字节的形式读取并写入buffer中*/
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        /*后面三行为关闭输入输出流以及网络资源的固定格式*/
        out.close();
        in.close();
        connection.disconnect();
    }
}