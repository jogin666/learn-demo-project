package com.zy.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.zy.RocketMQConfig.ROCKETMQ_IP;

public class OrderConsumer {

    public static void main(String[] args) {
        try {
            orderConsume();
        } catch (Exception e) {

        }
    }

    public static void orderConsume() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("orderConsumerGroup");
        consumer.setNamesrvAddr(ROCKETMQ_IP);
        consumer.subscribe("orderTopic", "produceTag || payTag || finishTag");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            Random random = new Random();

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                consumeOrderlyContext.setAutoCommit(true);
                for (MessageExt msg : list) {
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "queueId=" + msg.getQueueId()
                            + ", content:" + new String(msg.getBody()));
                    try {
                        //模拟业务逻辑处理中...
                        TimeUnit.SECONDS.sleep(random.nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
