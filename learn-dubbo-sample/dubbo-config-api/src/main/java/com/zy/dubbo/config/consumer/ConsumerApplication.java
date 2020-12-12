package com.zy.dubbo.config.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.zy.dubbo.service.DubboService;

/**
 * @author: jogin
 * @date: 2020/9/12 17:40
 */
public class ConsumerApplication {

    public static void main(String[] args) {

        //消费者应有信息
        ApplicationConfig application = new ApplicationConfig();
        application.setName("consumer");
        //注册地址
        RegistryConfig registryConfig=new RegistryConfig();
//        registryConfig.setCheck(false);
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("127.0.0.1:2181");
        //消费者信息
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setThreads(2);
        // 引用服务信息
        ReferenceConfig<DubboService> referencer = new ReferenceConfig<>();
        referencer.setConsumer(consumerConfig);
        referencer.setApplication(application);
        referencer.setRegistry(registryConfig);
        referencer.setInterface(DubboService.class);
        referencer.setVersion("1.0.0");
        //TODO 无法使用直连的方式，消费服务
//        referencer.setUrl("dubbo://127.0.0.1:20890/say");
        DubboService dubboService = referencer.get();

        System.out.printf(dubboService.say("dubbo"));
    }
}
