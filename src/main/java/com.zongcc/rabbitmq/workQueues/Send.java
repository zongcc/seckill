package com.zongcc.rabbitmq.workQueues;

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
    private final static String QUEUE_NAME = "task_queue";

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

    public static String getPrefixNum() {
        Calendar calendar = Calendar.getInstance();
        Integer p = calendar.get(Calendar.YEAR);
        String year = p.toString();
        year = year.replaceAll("0","");
        return year;
    }

    public static void main(String[] argv) throws IOException {
        Connection connection = null;
        Channel channel = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
            //指定队列持久化
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            for(int i=0;i<100;i++){
                String message = getMessage(argv);
                //指定消息持久化
                channel.basicPublish("", QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent "+i+ " '"+ message + "'");
            }
        }finally {
            channel.close();
            connection.close();
        }

        System.out.println(getPrefixNum());

    }
}
