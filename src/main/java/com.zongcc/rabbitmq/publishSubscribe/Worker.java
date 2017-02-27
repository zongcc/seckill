package com.zongcc.rabbitmq.publishSubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Created by zongcc on 2017/1/1.
 */
public class Worker {

    private final static String EXCHANGE_NAME = "logs";

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }

    public static void main(String[] argv) throws IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            //声明消息路由的名称和类型
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //声明一个随机消息队列
            String queueName = channel.queueDeclare().getQueue();
            //绑定消息队列和消息路由
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            QueueingConsumer consumer = new QueueingConsumer(channel);
            //启动一个消费者
            channel.basicConsume(queueName, true, consumer);
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());

                System.out.println(" [x] Received '" + message + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
