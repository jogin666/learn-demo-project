package com.zy.rocketmq.mq.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author jogin
 * @Date 2020/11/28 14:18
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.consumer.topic}", consumerGroup = "${mq.consumer.group}"
        , messageModel = MessageModel.BROADCASTING)
public class ConsumerRocketMQListener implements RocketMQListener<MessageExt> {

    @SneakyThrows
    @Override
    public void onMessage(MessageExt message) {
        byte[] body = message.getBody();
        log.info("接收到 RocketMq 消息：{}", new String(body, "UTF-8"));
    }
}
