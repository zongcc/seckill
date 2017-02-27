package com.zongcc.rabbitmq.workQueues;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by zongcc on 2017/1/1.
 */
public class Recv {
    private final static String QUEUE_NAME = "task_queue";

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
            //指定队列持久化
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            //指定该消费者同时只接收一条消息
            channel.basicQos(1);
            QueueingConsumer consumer = new QueueingConsumer(channel);
            boolean autoAck = false;//默认是true
            //打开消息应答机制
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
            int count =0;
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println(" [x] Received '" + message + "'");
                doWork(message);
                System.out.println(" [x] Done "+ (++count));

                //返回接收到消息的确认信息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
