package com.zongcc.rabbitmq.publishSubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by zongcc on 2017/1/1.
 */
public class Send {
    private final static String EXCHANGE_NAME = "logs";

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
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.confirmSelect();

            // getMessage的实现
            String message = getMessage(argv);

            for(int i=0;i<10;i++) {
                //指定exchange的名字
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
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
