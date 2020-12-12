package com.zy.dubbo.springboot;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: jogin
 * @date: 2020/9/12 21:44
 */
@EnableDubbo
@SpringBootApplication
public class ProviderSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderSpringBootApplication.class,args);
    }
}
