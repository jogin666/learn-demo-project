package com.zy.producer;

import com.zy.RocketMQConfig;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import java.util.concurrent.TimeUnit;

public class AsyncProducer {

    public static void main(String[] args) {
        try {
            asyncProduce();
        } catch (Exception e) {

        }
    }

    static void asyncProduce() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("asynGroup");
        producer.setNamesrvAddr(RocketMQConfig.ROCKETMQ_IP);
        producer.start();
        producer.setRetryTimesWhenSendFailed(0);
        producer.setVipChannelEnabled(false);
        final int msgCount = 50;
        CountDownLatch2 countDownLatch2 = new CountDownLatch2(msgCount);
        for (int i = 0; i < msgCount; i++) {
            final int index = i;
            Message msg = new Message("asyncTopic", "asyncTag", ("Hello RocketMQ " + i)
                    .getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        countDownLatch2.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
