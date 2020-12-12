package com.zy.dubbo.consumer;

import com.zy.dubbo.service.DubboService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: jogin
 * @date: 2020/9/12 18:48
 */
public class ConsumerApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-spring-consumer.xml");
        DubboService service = context.getBean("dubboService", DubboService.class);
        System.out.println(service.say("dubbo"));
    }
}
