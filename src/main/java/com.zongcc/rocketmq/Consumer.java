package com.zongcc.rocketmq;

/**
 * Created by chunchengzong on 2017-08-10.
 */
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

//消费者 push
public class Consumer {

    /**
     * 当前例子是Consumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br>
     * 但是实际Consumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
     *
     * @throws MQClientException
     */
    public static void main(String[] args) throws MQClientException {
        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ConsumerGroupName需要由应用来保证唯一 ,最好使用服务的包名区分同一服务,一类Consumer集合的名称，这类Consumer通常消费一类消息，且消费逻辑一致
         * PushConsumer：Consumer的一种，应用通常向Consumer注册一个Listener监听器，Consumer收到消息立刻回调Listener监听器
         * PullConsumer：Consumer的一种，应用通常主动调用Consumer的拉取消息方法从Broker拉消息，主动权由应用控制
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
                "ConsumerGroupName");
        // //nameserver服务
        consumer.setNamesrvAddr("10.10.125.43:9876;10.10.126.200:9876");
        consumer.setInstanceName("Consumber");
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置批量消费个数(最多10个)
        consumer.setConsumeMessageBatchMaxSize(10);

        /**
         * 订阅指定topic下tags分别等于TagA或TagC或TagD
         */
        //consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
        /**
         * 订阅指定topic下所有消息<br>
         * 注意：一个consumer对象可以订阅多个topic
         *
         */
        consumer.subscribe("TopicTest", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                ///接收消息个数msgs.size()
                System.out.println(Thread.currentThread().getName()
                        + " Receive New Messages: " + msgs.size());
                for (MessageExt msg:msgs) {
                    try {
                        System.out.println("current time is "+new Date()+ new String(msg.getBody(),
                                "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         */
        consumer.start();
        System.out.println("ConsumerStarted.");
    }

}