package com.zongcc.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by zongcc on 2017/1/1.
 */
public class Send {
    private final static String EXCHANGE_NAME = "direct_logs";

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    public static void main(String[] argv) throws IOException {
        Connection connection = null;
        Channel channel = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();

            //声明exchange名字以及类型
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // getMessage的实现
            String message = getMessage(argv);

            for(int i=0;i<10;i++) {
                //指定exchange的名字
                channel.basicPublish(EXCHANGE_NAME, "info", null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            channel.close();
            connection.close();
        }

    }
}
