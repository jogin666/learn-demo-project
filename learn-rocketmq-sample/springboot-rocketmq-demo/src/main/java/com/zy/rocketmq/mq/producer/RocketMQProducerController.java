package com.zy.rocketmq.mq.producer;

import lombok.SneakyThrows;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jogin
 * @Date 2020/11/28 14:19
 */
@RestController
public class RocketMQProducerController {

    @Autowired
    private RocketMQTemplate mqTemplate;

    @Value("${mq.producer.topic}")
    private String msgTopic;
    @Value("${mq.producer.message.tag}")
    private String msgTag;

    @SneakyThrows
    @GetMapping("/sendMsg")
    public String sendMQMessage(@RequestParam("msgBody") String msgBoyd) {
        Message msg = new Message();
        msg.setBody(msgBoyd.getBytes(RemotingHelper.DEFAULT_CHARSET));
        msg.setTopic(msgTopic);
        msg.setTags(msgTag);
        SendResult result = mqTemplate.getProducer().send(msg);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            return "success";
        }
        return "failure";
    }

    @GetMapping("/test")
    public String fun() {
        return mqTemplate.getProducer().getNamesrvAddr();
    }
}
