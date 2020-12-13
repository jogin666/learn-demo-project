package com.zy.producer;

import com.zy.RocketMQConfig;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class OneWayProducer {

    public static void main(String[] args) {
        try {
            sendMessageByOneWay();
        } catch (Exception e) {
        }
    }

    static void sendMessageByOneWay() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("oneWayGroup");
        producer.setNamesrvAddr(RocketMQConfig.ROCKETMQ_IP);
        producer.start();
        for (int i = 0; i < 50; i++) {
            Message msg = new Message("oneWayTopic", ("hello RocketMq").getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result = producer.send(msg);
            producer.sendOneway(msg);
        }
        producer.shutdown();
    }
}
