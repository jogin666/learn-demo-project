package com.zy.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author: jogin
 * @date: 2020/9/12 18:46
 */
public class ProviderApplication {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-spring-provider.xml");
        context.start();

        System.in.read();
    }
}
