package com.zy.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.zy.RocketMQConfig.ROCKETMQ_IP;

public class Consumer {

    /**
     * 维护消息队列的消费偏移量
     */
    public static final HashMap<MessageQueue, Long> OFFSET_TABLE = new HashMap<>();

    public static void main(String[] args) {
        try {
            consumeByPush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 推送模式
     *
     * @throws MQClientException
     */
    public static void consumeByPush() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumerGroup");
        consumer.setNamesrvAddr(ROCKETMQ_IP);
        consumer.subscribe("LearnRocketMqTopic", "syncTag");
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }

    /**
     * 拉取模式：该模式下需要消费者维护消息的消费的偏移量
     *
     * @throws MQClientException
     */
    public static void consumeByPull() throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("consumerGroup");
        consumer.setNamesrvAddr(ROCKETMQ_IP);
        consumer.start();
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("syncTopic");
        for (MessageQueue mq : mqs) {
            try {
                while (true) {
                    long offset = consumer.fetchConsumeOffset(mq, true);
                    System.out.println("consumer from the queue: " + mq + ": " + offset);
                    PullResult pullResult = consumer.pullBlockIfNotFound(mq, "*", getMessageQueueOffset(mq), 32);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                            for (MessageExt m : messageExtList) {
                                System.out.println(new String(m.getBody()));
                            }
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break;
                        case OFFSET_ILLEGAL:
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        consumer.shutdown();
    }

    /**
     * 保存上次消费的消息下标
     *
     * @param mq
     * @param nextBeginOffset
     */
    private static void putMessageQueueOffset(MessageQueue mq, long nextBeginOffset) {
        OFFSET_TABLE.put(mq, nextBeginOffset);
    }

    /**
     * 获取上次消费的消息的下标
     *
     * @param mq
     * @return
     */
    private static Long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSET_TABLE.get(mq);
        if (offset != null) {
            return offset;
        }
        return 0l;

    }
}
