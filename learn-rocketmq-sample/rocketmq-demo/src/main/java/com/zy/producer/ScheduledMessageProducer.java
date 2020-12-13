package com.zy.producer;

import com.zy.RocketMQConfig;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class ScheduledMessageProducer {

    public static void main(String[] args) {
        try {
            sendScheduledMessage();
        } catch (Exception e) {

        }
    }

    static void sendScheduledMessage() throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr(RocketMQConfig.ROCKETMQ_IP);
        producer.setVipChannelEnabled(false);
        // 启动生产者
        producer.start();
        int msgTotal = 100;
        for (int i = 0; i < msgTotal; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);
            // 发送消息
            producer.send(message);
        }
        // 关闭生产者
        producer.shutdown();
    }
}
