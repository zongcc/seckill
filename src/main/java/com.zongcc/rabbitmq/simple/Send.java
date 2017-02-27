package com.zongcc.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by zongcc on 2017/1/1.
 */
public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws IOException {
        Connection connection = null;
        Channel channel = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }catch (IOException e){
            channel.close();
            connection.close();
            e.printStackTrace();
        }finally {
            channel.close();
            connection.close();
        }
    }
}
